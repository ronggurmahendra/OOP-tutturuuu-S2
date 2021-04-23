package GUI.Game;
import java.util.ArrayList;
import java.util.List;

import GUI.AlertBox;
import GUI.BreedConfirm.BreedConfirm;
import GUI.DetailEngimon.DetailEngimon;
import GUI.ReplaceSkill.ReplaceSkill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.java.battle.Battle;
import main.java.element.Element;
import main.java.engimon.Engidex;
import main.java.engimon.Engimon;
import main.java.engimon.species.*;
import main.java.exception.SkillFullException;
import main.java.inventory.Skill_Item;
import main.java.map.*;
import GUI.BattleConfirm.BattleConfirm;
import main.java.player.Player;
import main.java.skill.Skidex;
import main.java.skill.Skill;

import java.util.concurrent.atomic.AtomicInteger;

public class GameController {
    // Gripane tempat peta dibentuk
    @FXML private GridPane MapGridPane;

    // Tombol movement
    @FXML private Button btn_w;
    @FXML private Button btn_a;
    @FXML private Button btn_s;
    @FXML private Button btn_d;

    // Tombol lain
    @FXML private Button btn_exit;

    // Tabel (Inventory)
    @FXML private TableView table_Engimon;
    @FXML private TableView table_Item;

    // Data active engimon
    @FXML private AnchorPane active_engimonPane;
    @FXML private ImageView active_el1;
    @FXML private ImageView active_el2;
    @FXML private ImageView active_sprite;
    @FXML private Label active_speciesName;
    @FXML private Label active_name;
    @FXML private Label active_level;
    @FXML private Label active_skillPower;
    @FXML private Label turnGUI;

    // Background tiap petak peta
    private  Image grass;
    private  Image sea;
    private  Image tundra;
    private  Image mountain;

    // Giliran
    private Integer turn;

    // Data peta
    private Map map;

    // Player
    private Player player;
    // Dummy
    // private List<Engimon> e;
    // private List<Skill_Item> f;
    private Engimon activeEngimon;


    // Inisialisasi komponen peta
    @FXML private void initialize(){
        // Load resources yang diperlukan sekaligus refresh GUI
        Engidex.initEngidex();
        Skidex.initSkill();
        try{
            this.turn =0;
            this.map = new Map(20, 10, "map.txt");
            this.player = new Player();
            this.loadImage();
            this.refreshMapGUI();
            this.activeEngimon = this.player.getActiveEngimon();

            // Dummy
            //  this.e = new ArrayList<Engimon>();
            //  this.e.add(new Cryo("Hello",3));
            //  this.e.add(new Pyro("Hello2",2));
            //  this.e.add(new Frozen("Helo3",3));
            // this.f = new ArrayList<Skill_Item>();
            // this.f.add(new Skill_Item(new Skill("Fire Breath", "Hah Naga!", 20, Element.Fire)));
            // this.f.add(new Skill_Item(new Skill("Kondum blast", "Rasa Stroberi!", 20, Element.Ice)));
            this.setupInventory();
            this.refreshInventory();
            // this.activeEngimon = this.e.get(0);
            this.refreshActiveEngimonGUI();

        } catch ( Exception e){
            AlertBox.displayWarning(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    public void loadImage(){
        // Melakukan load image
        this.grass = new Image("main/resources/grass.png",35,35,false,false);
        this.sea = new Image("main/resources/sea.png",35,35,false,false);
        this.tundra = new Image("main/resources/tundra.png",35,35,false,false);
        this.mountain = new Image("main/resources/mountain.png",35,35,false,false);
    }
    public void refreshActiveEngimonGUI(){
        if(this.activeEngimon == null){
            this.active_engimonPane.setVisible(false);
        } else {
            this.active_sprite.setImage(this.activeEngimon.getSprite(130,130));
            this.active_speciesName.setText(this.activeEngimon.getSpecies());
            this.active_name.setText(this.activeEngimon.getName());
            this.active_level.setText(Integer.toString(this.activeEngimon.getLevel()));
            this.active_skillPower.setText(Integer.toString(Battle.sumOfSkillPower(this.activeEngimon)));
            this.active_el1.setImage(Element.getSpriteEl(this.activeEngimon.getElement().get(0),30.0,30.0));
            if (this.activeEngimon.isOneElement()){
                this.active_el2.setImage(null);
            } else {
                this.active_el2.setImage(Element.getSpriteEl(this.activeEngimon.getElement().get(1),30.0,30.0));
            }
        }
    }
    public void refreshMapGUI(){
        // Melakukan load ulang peta berdasarkan kondisi peta yang ada di array
        this.turnGUI.setText(Integer.toString(this.turn));
        // Set ulang turn

        // Membersihkan isi gridpane peta
        this.MapGridPane.getChildren().clear();

        // Loop untuk tiap tile yang ada di peta
        for (int i=0; i< 10; i++){
            for (int j= 0; j < 20; j++){
                // Instansiasi pane baru
                StackPane Npane = new StackPane();

                // Mendapatkan info tile sekarang
                Mapelem tile = this.map.getMapElem().get(i).get(j);

                // Menggambar pane dengan background sesuai dengan type peta
                switch (tile.get_type()){
                    case "grassland":
                        Npane.getChildren().add(new ImageView(this.grass));
                        break;
                    case "sea":
                        Npane.getChildren().add(new ImageView(this.sea));
                        break;
                    case("tundra"):
                        Npane.getChildren().add(new ImageView(this.tundra));
                        break;
                    case("mountains"):
                        Npane.getChildren().add(new ImageView(this.mountain));
                        break;
                    default:
                        Npane.setStyle("-fx-background-color: black");
                }
                // Memasukkan pane kedalam gridpane
                this.MapGridPane.add(Npane,j,i);

                // Menambahkan gambar pemain atau engimon active di pane apabila tile sekarang ada player/engimon active
                if (tile.get_symbol() != '-' && tile.get_symbol() != '*' && tile.get_symbol() !='^' &&tile.get_symbol() !='o'){
                    switch (tile.get_symbol()){
                        case 'P':
                            Image player = new Image("main/resources/player.png",35,35,false,false);
                            ImageView pl = new ImageView(player);
                            Npane.getChildren().add(pl);
                            Npane.setAlignment(pl, Pos.CENTER);
                            break;
                        case 'X':
                            Image activeEngimon = new Image("main/resources/activeEngimon.png",30,30,false,false);
                            ImageView ae = new ImageView(activeEngimon);
                            Npane.getChildren().add(ae);
                            Npane.setAlignment(ae, Pos.CENTER);
                            break;
                    }
                }
                // Menambahkan gambar engimon liar di pane apabila tile sekarang ada engimon liar
                if(tile.get_engimonExist() == Boolean.TRUE){
                    // Mendapatkan foto engimon yang sesuai dengan engimon yang ada di tile sekarang
                    ImageView engimon = new ImageView(tile.get_engimon().getSprite(35,35));

                    // Mendapatkan aura engimon yang sesuai dengan engimon yang ada di tile sekarang
                    DropShadow ds = tile.get_engimon().getAura();
                    ds.setBlurType(BlurType.GAUSSIAN); ds.setRadius(1.5); ds.setSpread(1.5);
                    engimon.setEffect(ds);

                    // Menambahkan engimon dan aura ke pane
                    Npane.getChildren().add(engimon);
                    Npane.setAlignment(engimon, Pos.CENTER);
                }
            }
        }
    }
    // Melakukan setup inventory engimon dan skill item
    public void setupInventory(){
        TableColumn<Engimon, String> speciesColumn = new TableColumn<>("Species");
        speciesColumn.setMinWidth(70);
        speciesColumn.setSortable(false);
        speciesColumn.setCellValueFactory(new PropertyValueFactory<>("species"));

        TableColumn<Engimon, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(90);
        nameColumn.setSortable(false);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Engimon, List<String>> elementColumn = new TableColumn<>("Element");
        elementColumn.setMinWidth(90);
        elementColumn.setSortable(false);
        elementColumn.setCellValueFactory(new PropertyValueFactory<>("element"));

        TableColumn<Engimon, Integer> levelColumn = new TableColumn<>("Level");
        levelColumn.setMinWidth(30);
        levelColumn.setSortable(false);
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));

        this.table_Engimon.getColumns().addAll(speciesColumn,nameColumn,elementColumn,levelColumn);
        this.table_Engimon.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        TableColumn<Skill_Item, Integer> quantityColumn = new TableColumn<>("Qty");
        quantityColumn.setMinWidth(25);
        quantityColumn.setSortable(false);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Skill_Item, String> skillNameColumn = new TableColumn<>("Name");
        skillNameColumn.setMinWidth(90);
        skillNameColumn.setSortable(false);
        skillNameColumn.setCellValueFactory(new PropertyValueFactory<>("skillName"));

        TableColumn<Skill_Item, List<String>> skillElementColumn = new TableColumn<>("Element");
        skillElementColumn.setMinWidth(90);
        skillElementColumn.setSortable(false);
        skillElementColumn.setCellValueFactory(new PropertyValueFactory<>("element"));

        TableColumn<Skill_Item, Integer> basePowerColumn = new TableColumn<>("BP");
        basePowerColumn.setMinWidth(25);
        basePowerColumn.setSortable(false);
        basePowerColumn.setCellValueFactory(new PropertyValueFactory<>("basePower"));

        this.table_Item.getColumns().addAll(quantityColumn,skillNameColumn,skillElementColumn,basePowerColumn);
        this.table_Item.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

    }
    // Masih dummy
    public ObservableList<Engimon> getEngi(){
        ObservableList<Engimon> products = FXCollections.observableArrayList();
    //  products.addAll(this.e);
        products.addAll(this.player.getInventory().getEngimons());
        return products;
    }
    // Masih dummy
    public ObservableList<Skill_Item> getItem(){
        ObservableList<Skill_Item> products = FXCollections.observableArrayList();
        // products.addAll(this.f);
        products.addAll(this.player.getInventory().getItems());
        return products;
    }

    // Masih dummy
    public void refreshInventory(){
        this.table_Engimon.setItems(getEngi());
        this.table_Item.setItems(getItem());
    }

    // Inventory Command
    // Breed Engimon Command
    public void breedEngimon(){
        try{
            if ((this.table_Engimon.getSelectionModel().getSelectedIndices()).size() != 2) {
                throw new Exception("Pilihlah tepat dua engimon untuk melakukan breeding");
            } else {
                Integer indexEngimon1 = (Integer) this.table_Engimon.getSelectionModel().getSelectedIndices().get(0);
                Integer indexEngimon2 = (Integer) this.table_Engimon.getSelectionModel().getSelectedIndices().get(1);
//                Boolean isBreed = BreedConfirm.display(this.e.get(indexEngimon1),this.e.get(indexEngimon2));
                Boolean isBreed = BreedConfirm.display(this.player.getInventory().getEngimon(indexEngimon1),this.player.getInventory().getEngimon(indexEngimon2));
                if (isBreed){
                    // Prosedur breeding
                    AlertBox.displayWarning("Kawin kuy");
                    this.refreshInventory();
                    this.refreshActiveEngimonGUI();
                } else {
                    AlertBox.displayWarning("Gajadi kawin");
                }
            }
        } catch (Exception e){
            AlertBox.displayWarning(e.getMessage());
        }
    }

    // Rename engimon command
    public void renameEngimon(){
        try{
            if ((this.table_Engimon.getSelectionModel().getSelectedIndices()).size() != 1) {
                throw new Exception("Pilihlah tepat satu engimon untuk di rename");
            } else {
                String newName = AlertBox.displayAskNewName();
                if(newName != null){
                    // Prosedur rename engimon (detilnya di player, disini tinggal panggil aja)
                    AlertBox.displayWarning(newName);
                    this.refreshInventory();
                    this.refreshActiveEngimonGUI();
                }
            }
        } catch (Exception e){
            AlertBox.displayWarning(e.getMessage());
        }
    }

    // Interact with active engimon command
    public void interactActiveEngimon(){
        try{
            if (this.activeEngimon == null) {
                throw new Exception("Anda tidak mempunyai active engimon");
            } else {
                AlertBox.displayInteract(this.activeEngimon);
            }
        } catch (Exception e){
            AlertBox.displayWarning(e.getMessage());
        }
    }

    // Change Active Engimon Command
    public void changeActiveEngimon(){
        try{
            if ((this.table_Engimon.getSelectionModel().getSelectedIndices()).size() != 1) {
                throw new Exception("Pilihlah tepat satu engimon untuk menjadi active engimon");
            } else {
               this.activeEngimon = this.player.getInventory().getEngimon(this.table_Engimon.getSelectionModel().getSelectedIndex());
               refreshActiveEngimonGUI();
            }
        } catch (Exception e){
            AlertBox.displayWarning(e.getMessage());
        }
    }

    // Release Engimon command
    public void releaseEngimon(){
        try{
            if ((this.table_Engimon.getSelectionModel().getSelectedIndices()).size() != 1) {
                throw new Exception("Pilihlah tepat satu engimon untuk di examine");
            } else {
                // Prosedur release engimon dengan index tertentu (detilnya di player, disini tinggal panggil aja)
                this.refreshInventory();
                this.refreshActiveEngimonGUI();
            }
        } catch (Exception e){
            AlertBox.displayWarning(e.getMessage());
        }
    }
    // Examine Engimon command
    public void examineEngimon(){
        try{
            if ((this.table_Engimon.getSelectionModel().getSelectedIndices()).size() != 1) {
                throw new Exception("Pilihlah tepat satu engimon untuk di examine");
            } else {
                DetailEngimon.display(this.player.getInventory().getEngimon(this.table_Engimon.getSelectionModel().getSelectedIndex()));
                // DetailEngimon.display(this.e.get(this.table_Engimon.getSelectionModel().getSelectedIndex()));
            }
        } catch (Exception e){
            AlertBox.displayWarning(e.getMessage());
        }
    }

    // Throw item Command
    public void throwItem(){
        try{
            if ((this.table_Item.getSelectionModel().getSelectedIndices()).size() != 1) {
                throw new Exception("Pilihlah tepat satu item untuk dibuang");
            } else {
                Integer numOfItem = AlertBox.displayAskNumDropItems();
                if(numOfItem != null){
                    // Prosedur remove item sejumlah x (detilnya di player, disini tinggal panggil aja)
                    AlertBox.displayWarning(Integer.toString(numOfItem));
                    this.refreshInventory();
                }
            }
        } catch (Exception e){
            AlertBox.displayWarning(e.getMessage());
        }
    }

    // Use item Command
    public void useItem(){
        try{
            if (((this.table_Item.getSelectionModel().getSelectedIndices()).size() != 1) ||((this.table_Item.getSelectionModel().getSelectedIndices()).size() != 1)  ) {
                throw new Exception("Pilihlah tepat satu item dan satu engimon untuk learn skill");
            } else {
                // Engimon goingToLearn = this.e.get(this.table_Engimon.getSelectionModel().getSelectedIndex());
                Engimon goingToLearn = this.player.getInventory().getEngimon(this.table_Engimon.getSelectionModel().getSelectedIndex());
                // Skill_Item skilToLearn = this.f.get(this.table_Item.getSelectionModel().getSelectedIndex());
                Skill_Item skilToLearn = this.player.getInventory().getItem(this.table_Item.getSelectionModel().getSelectedIndex());
                goingToLearn.addSkill(skilToLearn);
            }
        }
        catch (SkillFullException e){
            //  Engimon goingToLearn = this.e.get(this.table_Engimon.getSelectionModel().getSelectedIndex());
            Engimon goingToLearn = this.player.getInventory().getEngimon(this.table_Engimon.getSelectionModel().getSelectedIndex());
            Integer indexReplace = ReplaceSkill.display(goingToLearn);
            if (indexReplace != null){
                // Algo replace skill
                // goingToLearn.replaceSkill(goingToLearn,indexReplace);
            }
        }
        catch (Exception e){
            AlertBox.displayWarning(e.getMessage());
        }
    }

    // Basic Command
    // Move command
    public void move(ActionEvent event){
        // Apabila tombol W A S D ditekan
        try{
            if(event.getSource().equals(this.btn_w)){
                this.map.move('w');
            } else if (event.getSource().equals(this.btn_a)){
                this.map.move('a');
            }else if (event.getSource().equals(this.btn_s)){
                this.map.move('s');
            }else {
                this.map.move('d');
            }
            this.turn++;
            if(this.turn%5==0){
                this.map.spawnRandomEngimon(10);
            }
            if(this.turn%20==0){
                this.map.evolveAllEngimon();
            }
            this.refreshMapGUI();
            this.map.printMap();

        } catch (Exception e){
            AlertBox.displayWarning(e.getMessage());
        }
    }

    // Battle Command
    public void Battle(){
        // Apabila tombol battle ditekan
        try{
            AtomicInteger x = new AtomicInteger(0);
            AtomicInteger y = new AtomicInteger(0);
            Engimon enemy = this.map.getNearbyEnemyEngimon(x,y);
            Boolean isBattle = BattleConfirm.display(this.activeEngimon,enemy);
            if (isBattle){
                this.map.removeEngimon(x.get(),y.get());
                AlertBox.displayWarning("Battle");
                this.refreshMapGUI();
            } else {
                AlertBox.displayWarning("Lho kok kabur");
            }

        } catch (Exception e){
            AlertBox.displayWarning(e.getMessage());
        }

    }

    // Exit command
    public void exit(){
        Stage stage = (Stage) this.btn_exit.getScene().getWindow();
        stage.close();
    }

}