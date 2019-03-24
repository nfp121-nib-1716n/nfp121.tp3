package question2;

import question1.PilePleineException;
import question1.PileVideException;

import java.util.Vector;
import java.util.NoSuchElementException;

/**
 * Décrivez votre classe PileVector ici.
 * 
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Pile3 implements PileI {

    private Vector<Object> v;
    private int capacite;
    private int taille;

    public Pile3() {
        this(CAPACITE_PAR_DEFAUT);
    }

    public Pile3(int taille) {
        if(taille <= 0) taille = CAPACITE_PAR_DEFAUT;
        v = new Vector<Object>(taille);
        this.capacite = taille;
        this.taille = 0;
    }

    public void empiler(Object o) throws PilePleineException {
        if(!estPleine()) {
            v.addElement(o);
            taille++;
        }
        else throw new PilePleineException();
    }

    public Object depiler() throws PileVideException {
        if(!estVide()){
            taille --;
            return v.remove(taille);
        }
        else throw new PileVideException();
    }

    public Object sommet() throws PileVideException {
        try{
            return v.lastElement();
        } catch (NoSuchElementException nsee){
            throw new PileVideException();
        }
    }

    public int taille() {
        return this.taille;
    }

    public int capacite() {
        return this.capacite;
    }

    public boolean estVide() {
        return taille == 0;
    }

    public boolean estPleine() {
        return taille == capacite;
    }

    public String toString() {
        String s = "[";
        int taille = this.taille();
        for(int i = taille-1; i >= 0; i--){
            s += v.get(i);
            if(i>0) 
                s+=", ";
        }
        s += "]";
        return s;
    }

    public boolean equals(Object object) {
        if(object == null) return false;
        
        //Assurer que l'objet en parametre est une instance d'une classe qui implemente PileI
        if(!(object instanceof PileI))
            return false;
        PileI pile = (PileI)object;
        
        //Meme instance?
        if(super.equals(object))
            return true;
        
        //Comparer les tailles et les capacites
        int capacite = this.capacite();
        int taille = this.taille();
        if(capacite != pile.capacite())
            return false;
        if(taille != pile.taille())
            return false;
            
        //Si les piles sont vides, elles sont egaux
        if(taille == 0) return true;
        
        //Comparaison element par element. J'ai considere que les piles sont egaux si leurs sequences d'elements sont egaux.
        Pile3 tempPile1 = new Pile3(taille);
        Pile3 tempPile2 = new Pile3(pile.taille());
        
        //Pour ne pas recopier du code, j'utilisera un boolean elementsEgaux
        boolean elementsEgaux;
        
        while (!this.estVide() && !pile.estVide()){
            try{
                elementsEgaux = false;
                if(this.sommet() == null){
                    if(pile.sommet() == null) 
                        elementsEgaux = true;
                }        
                else if(pile.sommet() == null){
                    if(this.sommet() == null) 
                        elementsEgaux = true;
                }  
                else if(this.sommet().equals(pile.sommet())){
                    elementsEgaux = true;
                }
                
                if(elementsEgaux){
                    tempPile1.empiler(this.depiler());
                    tempPile2.empiler(pile.depiler());
                }
                else{
                    remplirPile(tempPile1, this);
                    remplirPile(tempPile2, pile);
                    return false;
                }
            } catch(PilePleineException ppe){ppe.printStackTrace();}
            catch(PileVideException pve){pve.printStackTrace();}
        }
        
        //Retourner les elements a la pile initiale:
        remplirPile(tempPile1, this);
        remplirPile(tempPile2, pile);
        
        return true;
    }
    
    private void remplirPile(PileI p1, PileI p2){
        while(!p1.estVide()){
            try{
                p2.empiler(p1.depiler());
            } catch (PileVideException pve){pve.printStackTrace();}
            catch (PilePleineException ppe){ppe.printStackTrace();}
        }
    }

    // fonction fournie
    public int hashCode() {
        return toString().hashCode();
    }
    
    

}
