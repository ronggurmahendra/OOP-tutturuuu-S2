package main.java.skill.test;

import java.util.List;

import main.java.element.*;
import main.java.exception.SkidexNotInitalizedException;
import main.java.skill.*;


public class Test_Skill {
    public static void main(String[] args) {
        Skidex.initSkill();

        Skill s1 = new Skill("Fire Breath", "Hah Naga!", 20, Element.Fire);
        Skill s2 = new Skill(s1);
        System.out.println(s1);
        System.out.println(s2);


        s1.levelUp();
        s1.levelUp();
        s1.levelUp();
        if (s1.equals(s2)) {
            System.out.println("Yes");
        }
        System.out.println(s1.totalDamage());
        s1.print();
        s2.print();

        List<Skill> listSk;
        try {
            listSk = Skidex.getCompatibleSkill(Element.Water);
            for (Skill sk : listSk) {
                sk.printSimple();
            }
        } catch (SkidexNotInitalizedException e) {
            e.printStackTrace();
        }
        
        
    }
}
