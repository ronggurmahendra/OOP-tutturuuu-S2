package main.java.engimon.test;

import java.util.List;

import main.java.engimon.*;
import main.java.skill.*;

public class Test_Breeding {
    public static void main(String[] args) {
        Engidex.initEngidex();
        Skidex.initSkill();
        
        try {
            Engimon parentA = Engidex.getEngimonBySpecies("Pyro");
            Engimon parentB = Engidex.getEngimonBySpecies("Pyro");
            Engimon parentC = Engidex.getEngimonBySpecies("Pyro");
            System.out.println(parentA);
            System.out.println(parentB);

            // parentA.getSkill().get(0).levelUp();

            parentA.print();

            parentA.setLevel(8);
            parentB.setLevel(8);
            
            Engimon child1 = Breeding_Fountain.startBreeding(parentA, parentB);
            System.out.println("TESTING2");
            child1.getSkill().stream().forEach(a -> a.printSimple());
            // Engimon child2 = Breeding_Fountain.startBreeding(parentA, parentB);

            System.out.println(parentA.getSkill().get(0));
            System.out.println(parentB.getSkill().get(0));
            System.out.println(child1.getSkill().get(0));
            // System.out.println(child2.getSkill().get(0));

            child1.getSkill().get(0).levelUp();
        
            // child1.addSkill(sk);
            child1.print();
            System.out.println(child1);
            List<Skill> skillAnak1 = child1.getSkill();
            for (Skill skill : skillAnak1) {
                // System.out.println(skill);
                skill.printSimple();
            }

            System.out.println(":=======:");

            parentA.print();
            System.out.println(parentA);
            List<Skill> skillParent = parentA.getSkill();
            for (Skill skill : skillParent) {
                // System.out.println(skill);
                skill.printSimple();
            }

            // child2.print();
            // System.out.println(child2);
            // List<Skill> skillAnak2 = child2.getSkill();
            // for (Skill skill : skillAnak2) {
            //     // System.out.println(skill);
            //     skill.printSimple();
            // }

            

            // Breeding_Fountain.addSkillAnak(child,listSk);

            

            // Aman
            // if (Breeding_Fountain.isElementSimilar(parentA, parentB)) {
            //     System.out.println("Yes");
            // } else {
            //     System.out.println("No");
            // }

            // // Aman
            // List<Element> listEl = Breeding_Fountain.sortElementAdv(parentA, parentB);
            // for (Element element : listEl) {
            //     System.out.println(element);
            // }




        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // e.getCause();
            // System.err.println(e.getMessage());
            
            e.printStackTrace();
            
        }


    }
}
