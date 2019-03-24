package question3;

import question1.PilePleineException;
import question1.PileVideException;

import java.util.Stack;

public class Pile2<T> implements PileI<T>{
    /** par délégation : utilisation de la class Stack */
    private Stack<T> stk;
    /** la capacité de la pile */
    private int capacite;
    
    private int taille;

    /** Création d'une pile.
     * @param taille la "taille maximale" de la pile, doit être > 0
     */
    public Pile2(int taille){
        if(taille <= 0) 
            taille = CAPACITE_PAR_DEFAUT;
        this.stk = new Stack<T>();
        this.capacite = taille;
        this.taille = 0;
    }

    public Pile2(){
        this(CAPACITE_PAR_DEFAUT);
    }

    public void empiler(T o) throws PilePleineException{
        if(!estPleine()){
            stk.push(o);
            taille++;
        }
        else throw new PilePleineException();
    }

    public T depiler() throws PileVideException{
        if(!estVide()){
            taille--;
            return stk.pop();
        }
        else throw new PileVideException();
    }

    public T sommet() throws PileVideException{
       if(!estVide()){
            return stk.peek();
        }
        else throw new PileVideException();
    }
    
    /**
     * Effectue un test de l'etat de la pile.
     * 
     * @return vrai si la pile est vide, faux autrement
     */
    public boolean estVide() {
        return taille == 0;
    }

    /**
     * Effectue un test de l'etat de la pile.
     * 
     * @return vrai si la pile est pleine, faux autrement
     */
    public boolean estPleine() {
        return taille == capacite;
    }

    public String toString() {
        Pile2 pileTemp = new Pile2(capacite());
        Object tempObject = new Object();
        String s = "[";
        while (!estVide()){
            try{
                tempObject = depiler();
            } catch (PileVideException pve){pve.printStackTrace();}
            s += tempObject.toString();
            try{
                pileTemp.empiler(tempObject);
            } catch (PilePleineException ppe){ppe.printStackTrace();}
            if(!estVide())
                s += ", ";
        }
        remplirPile(pileTemp, this);
        return s + "]";
    }
    
    private void remplirPile(PileI p1, PileI p2){
        while(!p1.estVide()){
            try{
                p2.empiler(p1.depiler());
            } catch (PileVideException pve){pve.printStackTrace();}
            catch (PilePleineException ppe){ppe.printStackTrace();}
        }
    }

    public boolean equals(Object object){
        //Assurer que l'objet en parametre est une instance d'une classe qui implemente PileI
        if(!(object instanceof PileI))
            return false;
            
        PileI<T> pile;
        
        try{
            pile = (PileI<T>)object;
        } catch (ClassCastException cce){return false;}
        
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
        Pile2 <T> tempPile1 = new Pile2(taille);
        Pile2 <T> tempPile2 = new Pile2(pile.taille());
        
        while (!this.estVide() && !pile.estVide()){
            
            try{
                if(this.sommet().equals(pile.sommet())){
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

    // fonction fournie
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * Retourne le nombre d'element d'une pile.
     * 
     * @return le nombre d'element
     */
    public int taille() {
        return this.taille;
    }

    /**
     * Retourne la capacite de cette pile.
     * 
     * @return le nombre d'element
     */
    public int capacite() {
        return this.capacite;
    }

}