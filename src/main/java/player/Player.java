package main.java.player;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

import main.java.element.Element;
import main.java.engimon.*;
import main.java.engimon.species.*;
import main.java.exception.ItemNotEnoughAmountException;
import main.java.exception.SkillElementNotCompatibleException;
import main.java.inventory.Inventory;
import main.java.inventory.Skill_Item;
import com.google.gson.Gson;
import main.java.exception.*;
import main.java.skill.Skill;

public class Player {
    private Engimon activeEngimon;
    private Inventory<Engimon,Skill_Item> inventoryEntity;

    public Player(){
        this.inventoryEntity = new Inventory<Engimon,Skill_Item>();
        Engimon a1 = new Pyro("Pyro1",3);
        Engimon a2 = new Hydro("Hydro1",3);
        Engimon a3 = new Electro("Electro1",3);
        Engimon a4 = new Geo("Geo1",3);
        Engimon a5 = new Cryo("Cryo1",3);
        a1.setLevel(40);
        a2.setLevel(40);
        a3.setLevel(40);
        a4.setLevel(40);
        a5.setLevel(40);
        try{
//            a1.addSkill(new Skill("Panas1", "Hottt1", 10, Element.Fire));
//            a1.addSkill(new Skill("Panas2", "Hottt2", 10, Element.Fire));
//            a1.addSkill(new Skill("Panas3", "Hottt3", 10, Element.Fire));
            this.inventoryEntity.addEngimon(a2);
            this.inventoryEntity.addEngimon(a1);
            this.inventoryEntity.addEngimon(a3);
            this.inventoryEntity.addEngimon(a4);
            this.inventoryEntity.addEngimon(a5);
        }catch (Exception e){
            System.out.println("Something went Wrong");
        }

        this.activeEngimon = this.inventoryEntity.getEngimon(0);
    }

//    public Player(String JsonFIle ){
//        java.net.URL url = this.getClass().getResource(JsonFIle);
//        File jsonFile = new File(url.getFile());
//        System.out.println("Full path of file: " + jsonFile);
//
//        try{
//            BufferedReader br = new BufferedReader(new FileReader(JsonFIle));
//            Player temp = new Gson().fromJson(br, Player.class);
//            this = temp;
//        }catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//    }



    public Inventory getInventory(){
        return this.inventoryEntity;
    }

    public void ReleaseEngimon(Integer i){
        if(this.getInventory().getEngimons().get(i).equals(this.activeEngimon)){
            this.activeEngimon = null;
        }
        this.getInventory().deleteEngimon(i);
    }

    public void PrintInventory(){
        inventoryEntity.printInventory();
    }
    public void changeActiveEngimon(int i){
        activeEngimon = inventoryEntity.getEngimon(i);
    }
    public Engimon getActiveEngimon(){
        return activeEngimon;
    }
    public void KillActiveEngimon(){
        Integer isDead = inventoryEntity.KillEngimon(activeEngimon);
        if (isDead == 1){
            this.activeEngimon = null;
        }
    }
    public void renameEngimon(int i, String Name){
        inventoryEntity.getEngimon(i).setName(Name);
    }
    public void setActiveEngimon(Engimon e){
        this.activeEngimon = e;
    }

//    public void save() throws Exception {
//        Gson gson = new Gson();
//        String filePath = "";
//        gson.toJson(this, new FileWriter(filePath));
//        System.out.println(new Gson().toJson(this));
//    }

    public static void save(Player player)throws Exception{
        Gson gson = new Gson();
        String filePath = "./wkkw.json";
        gson.toJson(player, new FileWriter(filePath));
        System.out.println(new Gson().toJson(player));

        try {
            File myObj = new File("./playerSaveFile.json");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter("./playerSaveFile.json");
            myWriter.write(new Gson().toJson(player));
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static Player load(String JsonFIle )throws IOException   {

        //java.net.URL url = JsonFIle.getClass().getResource(JsonFIle);
        try{
            File jsonFile = new File(JsonFIle);
            System.out.println("Full path of file: " + jsonFile);
        }catch (Exception e){
            System.out.println("File Not Found");
            throw e;
        }
        try{
            BufferedReader br = new BufferedReader(new FileReader(JsonFIle));
            Player temp = new Gson().fromJson(br, Player.class);
            return temp;
        }catch (IOException e)
        {

            e.printStackTrace();

            throw e;
        }

    }

}
