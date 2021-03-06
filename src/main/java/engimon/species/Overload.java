package main.java.engimon.species;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.java.engimon.*;
import main.java.element.*;
import main.java.skill.*;

public class Overload extends Engimon {
    /* CONSTRUCTORS */
    // ctor tanpa parent
    public Overload(String name, boolean isWild) {
        super(name, isWild);
        this.species = "Overload";
        this.element.add(Element.Fire);
        this.element.add(Element.Electric);
        this.skill.add(new Skill("Korsleting", "Api berpadu dengan Listrik", 20, Element.Fire, Element.Electric));
        this.slogan = "Anata ga namae wo yobu~";
    }
    // ctor dengan parent
    public Overload(String name, boolean isWild, Parent parent) {
        super(name, isWild, parent);
        this.species = "Overload";
        this.element.add(Element.Fire);
        this.element.add(Element.Electric);
        this.skill.add(new Skill("Korsleting", "Api berpadu dengan Listrik", 20, Element.Fire, Element.Electric));
        this.slogan = "Anata ga namae wo yobu~";
    }
    // ctor tanpa parent
    public Overload(String name, int lives) {
        super(name, lives);
        this.species = "Overload";
        this.element.add(Element.Fire);
        this.element.add(Element.Electric);
        this.skill.add(new Skill("Korsleting", "Api berpadu dengan Listrik", 20, Element.Fire, Element.Electric));
        this.slogan = "Anata ga namae wo yobu~";
    }
    // ctor dengan parent
    public Overload(String name, int lives, Parent parent) {
        super(name, lives, parent);
        this.species = "Overload";
        this.element.add(Element.Fire);
        this.element.add(Element.Electric);
        this.skill.add(new Skill("Korsleting", "Api berpadu dengan Listrik", 20, Element.Fire, Element.Electric));
        this.slogan = "Anata ga namae wo yobu~";
    }
    @Override
    public Image getSprite(double rw, double rh){
        if (this.getLevel() < 50){
            return new Image("./main/resources/Overload1.png",rw,rh,false,false);
        } else {
            return new Image("./main/resources/Overload2.png",rw,rh,false,false);
        }
    }

    @Override
    public DropShadow getAura() {
        return new DropShadow(35, Color.DARKORANGE);
    }

    @Override
    public String getSlogan() {
        return this.slogan;
    }
}
