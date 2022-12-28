package com.example;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * JavaFX App
 */
public class App extends Application implements Serializable{
    private static Course[] planArray;
    private static Section[] sectionsArray;
    private static Student student;
    private static ArrayList availableSections;
    private static Scene scene;
    private static Scene scene2;
    BorderPane root = new BorderPane();
    Button savedScheduleButton = new Button("Start with a saved schedule");
    Button nextButton = new Button("Next");
    Label label = new Label("Add Sections to Basket");
    HBox header = new HBox(75);
    HBox AddRemove = new HBox(75);
    Label sec= new Label();
    // demoSection should be replaced with arraylist of available Sections
    // Section demoSection = new Section("ICS 104-05,LEC,13046,Introduction to Programming in Python and C,S ARAFAT,UT,0800-0850,24-121,Open,Closed");
    ListView<Section> listView = new ListView<Section>();
    static ArrayList<Section> Basket = new ArrayList<Section>();
    static ArrayList<Section> ScheduleSections = new ArrayList<>();
    static ArrayList<Section> SundaySections = new ArrayList<>();
    static ArrayList<Section> MondaySections = new ArrayList<>();
    static ArrayList<Section> TusedaySections = new ArrayList<>();
    static ArrayList<Section> WednesdaySections = new ArrayList<>();
    static ArrayList<Section> ThursdaySections = new ArrayList<>();

    static Button lb19 = new Button("NO Status");
    static Button save = new Button("Save Schdule");

    static ListView<String> coursesListView = new ListView<>();

    static GridPane vBoxes = new GridPane();
    static GridPane timesBox = new GridPane();

    static VBox sunday = new VBox();
    static VBox monday = new VBox();
    static VBox tuesday = new VBox();
    static VBox wednesday = new VBox();
    static VBox thursday = new VBox();
    static int colorint = 0;

    static double addedHeight[] = new double[5];
    String currentText;
    public static ArrayList<Section> getBasket(){
        return Basket;
    }
    @Override
    public void start(Stage stage) throws IOException {
        currentText="Current Basket:";
        sec.setText(currentText);
        stage.setMaximized(true);
        stage.setResizable(false);
        scene = new Scene(root, 1550, 800);
        header.getChildren().addAll(label,savedScheduleButton);
        header.setAlignment(Pos.CENTER);
        root.setTop(header);
        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        AddRemove.getChildren().addAll(addButton,removeButton);
        AddRemove.setAlignment(Pos.CENTER);
        // listView.getItems().add(demoSection); // (should be replaced with the code below)
        for (Object availableSection : availableSections) {
           listView.getItems().add((Section) availableSection); 
        }
        VBox vBox = new VBox(listView, AddRemove,sec);
        root.setPadding(new Insets(20));
        root.setCenter(vBox);
        root.setBottom(nextButton);
        stage.setTitle("Course offering KFUPM");
        stage.setScene(scene);
        stage.show();
        






        addButton.setOnAction(event -> {
            ObservableList selectedIndices = listView.getSelectionModel().getSelectedItems();

            for(Object o : selectedIndices){
                if(o instanceof Section & !Basket.contains((Section) o)){
                    Basket.add((Section) o);
                    sec.setText(currentText+"\n"+((Section) o).toString());
                    currentText=sec.getText();
                    } 
            }
            System.out.println(Basket);
        });

        removeButton.setOnAction(event -> {
            ObservableList selectedIndices = listView.getSelectionModel().getSelectedItems();

            for(Object o : selectedIndices){
                if(o instanceof Section){Basket.remove((Section) o);} 
            }
            currentText="Current Basket:";
            for(Section o: Basket){
                currentText=currentText+"\n"+o.toString();
            }
            sec.setText(currentText);
            System.out.println(Basket);
        });

        

        savedScheduleButton.setOnAction(event -> {
            TextInputDialog td = new TextInputDialog();
            td.setHeaderText("Enter the file ith saved Schedule");
            td.getDialogPane().setContentText("Enter Path : ");
            Optional<String> path = td.showAndWait();
            TextField input = td.getEditor();
            if(input.getText() != null && input.getText().toString().length() !=0){
                System.out.println(input.getText().toString());
                
                
                try(FileInputStream file= new FileInputStream(input.getText().toString());
                    ObjectInputStream inputScanner = new ObjectInputStream(file)){
                    ArrayList<Section> tempArray= (ArrayList<Section>) inputScanner.readObject();
                    for(Section i: tempArray){
                        if(!Basket.contains(i)){
                        Basket.add(i);
                        sec.setText(currentText+"\n"+i.toString());
                    }}
                    System.out.println(Basket);
                }
                catch(IOException e){
                    System.out.println("xxx");
                }
                catch(ClassNotFoundException e){
                    System.out.println("Data is not of type Section");
                }
            }
            else{
                // System.out.println("you did not enter path");
            }});
            ///////////////////////////////////////////////////////////// Haithems work
            
                
                timesBox.setGridLinesVisible(true);
        
                coursesListView.setOnMouseClicked(new ListViewHandler());
        
                sunday.setPrefWidth(130);
                sunday.setPadding(new Insets(0, 5, 0, 5));
                monday.setPrefWidth(130);
                monday.setPadding(new Insets(0, 5, 0, 5));
                tuesday.setPrefWidth(130);
                tuesday.setPadding(new Insets(0, 5, 0, 5));
                wednesday.setPrefWidth(130);
                wednesday.setPadding(new Insets(0, 5, 0, 5));
                thursday.setPrefWidth(130);
                thursday.setPadding(new Insets(0, 5, 0, 5));
        
                // Basketsections.add(new Section("ICS 308", 77, "UTR", "07:00-07:50", "13043", "24-121"));
                // Basketsections.add(new Section("ICS 108", 6, "M", "09:00-09:50", "13046", "24-121"));
                // Basketsections.add(new Section("ICS 208", 9, "MW", "10:00-11:25", "13049", "24-121"));
                // Basketsections.add(new Section("ICS 108", 7, "UTR", "08:00-08:50", "13246", "24-121"));
                // Basketsections.add(new Section("ICS 409", 45, "UT", "9:00-9:50", "13246", "24-121"));
                // Basketsections.add(new Section("ICS 805", 88, "UTR", "15:00-17:00", "13246", "24-121"));
                // Basketsections.add(new Section("ICS 905", 87, "UTR", "15:00-17:50", "13246", "24-121"));
                // Basketsections.add(new Section("ICS 705", 76, "T", "12:00-12:50", "13246", "24-121"));
                // Basketsections.add(new Section("ICS 985", 54, "R", "10:00-11:00", "13246", "24-121"));
        
                Label lb1 = new Label("Sun");
                Label lb2 = new Label("Mon");
                Label lb3 = new Label("Tue");
                Label lb4 = new Label("Wed");
                Label lb5 = new Label("Thu");
                Label lb0 = new Label("Register status");
        
                Label lb6 = new Label("Sections Basket");
                lb6.setTextFill(Color.GREEN);
                HBox hb = new HBox(lb6);
                hb.setAlignment(Pos.CENTER);
        
                lb1.setFont(new Font(STYLESHEET_CASPIAN, 22));
                lb2.setFont(new Font(STYLESHEET_CASPIAN, 22));
                lb3.setFont(new Font(STYLESHEET_CASPIAN, 22));
                lb4.setFont(new Font(STYLESHEET_CASPIAN, 22));
                lb5.setFont(new Font(STYLESHEET_CASPIAN, 22));
                lb6.setFont(new Font(STYLESHEET_CASPIAN, 28));
                lb0.setFont(new Font(STYLESHEET_CASPIAN, 28));
        
                GridPane daysBox = new GridPane();
                Label lb7 = new Label("7am");
                Label lb8 = new Label("8am");
                Label lb9 = new Label("9am");
                Label lb10 = new Label("10am");
                Label lb11 = new Label("11am");
                Label lb12 = new Label("12pm");
                Label lb13 = new Label("1pm");
                Label lb14 = new Label("2pm");
                Label lb15 = new Label("3pm");
                Label lb16 = new Label("4pm");
                Label lb17 = new Label("5pm");
        
                save.setStyle("-fx-background-color: #00ff00");
                save.setPrefSize(250, 40);
                save.setTextFill(Color.BLACK);
                lb19.setPrefSize(250, 40);
                lb19.setTextFill(Color.GREEN);
                save.setOnAction(new ButtonHandler2());
        
                lb7.setFont(new Font(STYLESHEET_CASPIAN, 20));
                lb8.setFont(new Font(STYLESHEET_CASPIAN, 20));
                lb9.setFont(new Font(STYLESHEET_CASPIAN, 20));
                lb10.setFont(new Font(STYLESHEET_CASPIAN, 20));
                lb11.setFont(new Font(STYLESHEET_CASPIAN, 20));
                lb12.setFont(new Font(STYLESHEET_CASPIAN, 20));
                lb13.setFont(new Font(STYLESHEET_CASPIAN, 20));
                lb14.setFont(new Font(STYLESHEET_CASPIAN, 20));
                lb15.setFont(new Font(STYLESHEET_CASPIAN, 20));
                lb16.setFont(new Font(STYLESHEET_CASPIAN, 20));
                lb17.setFont(new Font(STYLESHEET_CASPIAN, 20));
                lb19.setFont(new Font(STYLESHEET_CASPIAN, 28));
                save.setFont(new Font(STYLESHEET_CASPIAN, 28));
        
                daysBox.setHgap(80);
                daysBox.setAlignment(Pos.BOTTOM_CENTER);
                // GridPane.setHgrow(daysBox, Priority.ALWAYS);
                daysBox.add(lb1, 0, 2);
                daysBox.add(lb2, 1, 2);
                daysBox.add(lb3, 2, 2);
                daysBox.add(lb4, 3, 2);
                daysBox.add(lb5, 4, 2);
        
                VBox status = new VBox();
                status.setAlignment(Pos.CENTER);
                status.setPadding(new Insets(5, 20, 5, 20));
                status.getChildren().add(lb0);
                status.getChildren().add(lb19);
                status.getChildren().add(save);
        
                /////////////////////////////////////////////////
                // timesBox.setGridLinesVisible(true); // delete later
                timesBox.setPadding(new Insets(5, 5, 5, 5));
                GridPane.setMargin(daysBox, new Insets(20, 10, 10, 5));
                timesBox.setAlignment(Pos.CENTER_RIGHT);
                timesBox.add(lb7, 0, 1);
                timesBox.add(lb8, 0, 2);
                timesBox.add(lb9, 0, 3);
                timesBox.add(lb10, 0, 4);
                timesBox.add(lb11, 0, 5);
                timesBox.add(lb12, 0, 6);
                timesBox.add(lb13, 0, 7);
                timesBox.add(lb14, 0, 8);
                timesBox.add(lb15, 0, 9);
                timesBox.add(lb16, 0, 10);
                timesBox.add(lb17, 0, 11);
                GridPane.setVgrow(lb7, Priority.ALWAYS);
                GridPane.setVgrow(lb8, Priority.ALWAYS);
                GridPane.setVgrow(lb9, Priority.ALWAYS);
                GridPane.setVgrow(lb10, Priority.ALWAYS);
                GridPane.setVgrow(lb11, Priority.ALWAYS);
                GridPane.setVgrow(lb12, Priority.ALWAYS);
                GridPane.setVgrow(lb13, Priority.ALWAYS);
                GridPane.setVgrow(lb14, Priority.ALWAYS);
                GridPane.setVgrow(lb15, Priority.ALWAYS);
                GridPane.setVgrow(lb16, Priority.ALWAYS);
                GridPane.setVgrow(lb17, Priority.ALWAYS);
        
                // sections array should replaced with basket section list from prev scene
        
                coursesListView.setPadding(new Insets(5, 5, 5, 5));
        
                for (Section i : Basket)
                    coursesListView.getItems().add(i.toString2());
        
                vBoxes.setPadding(new Insets(0, 5, 0, 5));
                // vBoxes.setGridLinesVisible(true);
                vBoxes.setAlignment(Pos.TOP_CENTER);
                vBoxes.add(sunday, 0, 0);
                vBoxes.add(monday, 1, 0);
                vBoxes.add(tuesday, 2, 0);
                vBoxes.add(wednesday, 3, 0);
                vBoxes.add(thursday, 4, 0);
        
                GridPane pane = new GridPane();
                pane.setPadding(new Insets(15, 15, 15, 15));
                GridPane.setVgrow(timesBox, Priority.ALWAYS);
                GridPane.setHgrow(timesBox, Priority.ALWAYS);
                pane.setAlignment(Pos.CENTER);
                // pane.setGridLinesVisible(true);
                pane.add(daysBox, 1, 0);
                pane.add(timesBox, 0, 1);
                pane.add(coursesListView, 3, 1);
                pane.add(hb, 3, 0);
                pane.add(vBoxes, 1, 1);
                pane.add(status, 2, 0);
        
                pane.setStyle("-fx-background-color: grey;");
                Scene scene2 = new Scene(pane, 1550, 800);
        
                //primaryStage.setScene(scene);
               // primaryStage.show();
        
            
    
            nextButton.setOnAction(event -> {
                System.out.println("nnnn");
                System.out.println(Basket);
                stage.setScene(scene2);
                for (Section i : Basket)
                    coursesListView.getItems().add(i.toString2());
            });
        };
        




    

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


    

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        String[] x= fileToArrayHeader("src\\main\\java\\com\\example\\DegreePlan.csv");
        planArray= new Course[linesHeader("src\\main\\java\\com\\example\\DegreePlan.csv")];
        for(int i=0;i < planArray.length;i++){
            planArray[i]=new Course(x[i]);
        }
        System.out.println(Arrays.toString(planArray));
        student=new Student(fileToArray("src\\main\\java\\com\\example\\FinishedCourses.csv"));
        x= fileToArrayHeader("src\\main\\java\\com\\example\\CourseOffering.csv");
        sectionsArray=new Section[x.length];
        for(int i=0; i<x.length;i++){
            sectionsArray[i]=new Section(x[i]);
        }
        availableSections=  student.getOfferedAvaliableCourse(planArray, sectionsArray);
        System.out.println(availableSections);
        launch();
    }


    public static int linesHeader(String filePath){
        File file= new File(filePath);
        int counter=0;
        try (Scanner scan=new Scanner(file)){
            scan.nextLine();// header line, no need to count it
            while(scan.hasNextLine()){
            counter++;
            scan.nextLine();
            }
            return counter;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return -1;}
        }
        public static int lines(String filePath){// if the file has no header use this
            File file= new File(filePath);
            int counter=0;
            try (Scanner scan=new Scanner(file)){
                while(scan.hasNextLine()){
                counter++;
                scan.nextLine();
                }
                return counter;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return -1;
            }
    
    }
    //this method servees to create an array where each line without the header is an index
    public static String[] fileToArrayHeader(String filePath){

        File file= new File(filePath);
        int length= linesHeader(filePath);
        try(Scanner scan=new Scanner(file)){
            String[] returnString= new String[length];
            scan.nextLine();
            int j= 0;
            while(scan.hasNextLine()){
                returnString[j]=scan.nextLine();
                j++;
            }
            return returnString;
    
        }
        catch(IOException e){
            return null;}
        }
        public static String[] fileToArray(String filePath){// use this if file has no header

            File file= new File(filePath);
            int length= lines(filePath);
            try(Scanner scan=new Scanner(file)){
                String[] returnString= new String[length];
                int j= 0;
                while(scan.hasNextLine()){
                    returnString[j]=scan.nextLine();
                    j++;
                }
                return returnString;
        
            }
            catch(IOException e){
                return null;
            }
    

    }

}
class ListViewHandler implements EventHandler<MouseEvent> {

    public static boolean checkConflict(ArrayList<Section> basketSections, Section newSection) {
        boolean notConf = true;
        for (Section time : basketSections) {
            int[] testTime = time.period();
            int[] constantTime = newSection.period();
            if (!(((constantTime[0] - testTime[0]) > 0 && (constantTime[1] - testTime[0]) > 0
                    && (constantTime[0] - testTime[1]) > 0 && (constantTime[1] - testTime[1]) > 0)
                    || ((constantTime[0] - testTime[0]) < 0 && (constantTime[1] - testTime[0]) < 0
                            && (constantTime[0] - testTime[1]) < 0 && (constantTime[1] - testTime[1]) < 0))) {
                notConf = false;

            }

        }
        return notConf;
    }

    @Override
    public void handle(MouseEvent e) {
        String[] colors = { "-fx-background-color: #00FFFF", "-fx-background-color: #F0FFFF",
                "-fx-background-color: #0000FF", "-fx-background-color: #088F8F", "-fx-background-color: #00A36C",
                "-fx-background-color: #CCCCFF", "-fx-background-color: #B6D0E2", "-fx-background-color: #CD7F32    ",
                "-fx-background-color: #EADDCA", "-fx-background-color: #E1C16E", "-fx-background-color: #A52A2A",
                "-fx-background-color: #E97451  ", "-fx-background-color: #988558", "-fx-background-color: #F0E68C",
                "-fx-background-color: #800080  ", "-fx-background-color: #4A0404", "-fx-background-color: #808000" };
        ;
        String sectionString = ((ListView<String>) (e.getSource())).getSelectionModel().getSelectedItem();
        Section sectionChosed = null;
        boolean notConf = true;

        for (Section f : App.getBasket()) {
            if (f.toString2().equals(sectionString)) {

                sectionChosed = f;
                for (Section k : App.ScheduleSections) {
                    if (sectionChosed.getCourseName().equals(k.getCourseName())) {
                        notConf = false;

                    }
                }

            }

        }
        if (sectionChosed.getDays().equals("UTR")) {
            notConf = (notConf
                    && ListViewHandler.checkConflict(App.SundaySections, sectionChosed)
                    && ListViewHandler.checkConflict(App.TusedaySections, sectionChosed)
                    && ListViewHandler.checkConflict(App.ThursdaySections, sectionChosed));
        } else if (sectionChosed.getDays().equals("MW")) {

            notConf = notConf && ListViewHandler.checkConflict(App.MondaySections, sectionChosed)
                    && ListViewHandler.checkConflict(App.WednesdaySections, sectionChosed);
        } else if (sectionChosed.getDays().equals("UT")) {
            notConf = (notConf
                    && ListViewHandler.checkConflict(App.SundaySections, sectionChosed)
                    && ListViewHandler.checkConflict(App.TusedaySections, sectionChosed));
        } else if (sectionChosed.getDays().equals("U")) {
            notConf = (notConf
                    && ListViewHandler.checkConflict(App.SundaySections, sectionChosed));
        } else if (sectionChosed.getDays().equals("M")) {
            notConf = (notConf
                    && ListViewHandler.checkConflict(App.MondaySections, sectionChosed));
        } else if (sectionChosed.getDays().equals("T")) {
            notConf = (notConf
                    && ListViewHandler.checkConflict(App.TusedaySections, sectionChosed));
        } else if (sectionChosed.getDays().equals("W")) {
            notConf = (notConf
                    && ListViewHandler.checkConflict(App.WednesdaySections, sectionChosed));
        } else if (sectionChosed.getDays().equals("R")) {
            notConf = (notConf
                    && ListViewHandler.checkConflict(App.ThursdaySections, sectionChosed));
        }

        if (notConf) {
            App.coursesListView.getItems()
                    .remove(((ListView<String>) e.getSource()).getSelectionModel().getSelectedItem());
            System.out.println(e.getSource());
            double HEIGHT_PER_HOUR = App.timesBox.getHeight() / 11.0;
            App.ScheduleSections.add(sectionChosed);
            App.lb19.setText("Added!");
            App.lb19.setTextFill(Color.GREEN);
            String Days = sectionChosed.getDays();
            App.colorint++;
            if (App.colorint > 15) {
                App.colorint = 0;

            }
            for (int k = 0; k < Days.length(); k++) {
                int[] time = sectionChosed.period();
                double start = (((time[0] - 420.0) / 60.0) * HEIGHT_PER_HOUR);
                double height = ((time[1] - time[0]) / 60.0) * HEIGHT_PER_HOUR;
                Button section = new Button(sectionString);
                section.setStyle(colors[App.colorint]);

                section.setOnAction(new ButtonHandler());
                section.setWrapText(true);
                section.setPrefSize(130, height);
                Character temp = (Days.charAt(k));
                if (String.valueOf(temp).equals("U")) {
                    App.SundaySections.add(sectionChosed);
                    App.sunday.getChildren().add(section);
                    section.setTranslateY(start - App.addedHeight[0]);
                    App.addedHeight[0] += height;
                }
                if (String.valueOf(temp).equals("M")) {
                    App.MondaySections.add(sectionChosed);
                    App.monday.getChildren().add(section);
                    section.setTranslateY(start - App.addedHeight[1]);
                    App.addedHeight[1] += height;
                }
                if (String.valueOf(temp).equals("T")) {
                    App.TusedaySections.add(sectionChosed);
                    App.tuesday.getChildren().add(section);
                    section.setTranslateY(start - App.addedHeight[2]);
                    App.addedHeight[2] += height;
                }
                if (String.valueOf(temp).equals("W")) {
                    App.WednesdaySections.add(sectionChosed);
                    App.wednesday.getChildren().add(section);
                    section.setTranslateY(start - App.addedHeight[3]);
                    App.addedHeight[3] += height;
                }
                if (String.valueOf(temp).equals("R")) {
                    App.ThursdaySections.add(sectionChosed);
                    App.thursday.getChildren().add(section);
                    section.setTranslateY(start - App.addedHeight[4]);
                    App.addedHeight[4] += height;
                }

            }

        } else {
            App.lb19.setText("Conflict!");
            App.lb19.setTextFill(Color.RED);

            notConf = false;
        }

    }
}
class ButtonHandler implements EventHandler<ActionEvent> {

    public void removeSection(Section toRemovSection) {
        String[] colors = { "-fx-background-color: #00FFFF", "-fx-background-color: #F0FFFF",
                "-fx-background-color: #0000FF", "-fx-background-color: #088F8F", "-fx-background-color: #00A36C",
                "-fx-background-color: #CCCCFF", "-fx-background-color: #B6D0E2", "-fx-background-color: #CD7F32    ",
                "-fx-background-color: #EADDCA", "-fx-background-color: #E1C16E", "-fx-background-color: #A52A2A",
                "-fx-background-color: #E97451  ", "-fx-background-color: #988558", "-fx-background-color: #F0E68C",
                "-fx-background-color: #800080  ", "-fx-background-color: #4A0404", "-fx-background-color: #808000" };
        App.ScheduleSections.remove(toRemovSection);
        App.coursesListView.getItems().add(toRemovSection.toString2());
        String Days = toRemovSection.getDays();
        App.addedHeight[0] = 0.0;
        App.addedHeight[1] = 0.0;
        App.addedHeight[2] = 0.0;
        App.addedHeight[3] = 0.0;
        App.addedHeight[4] = 0.0;
        App.lb19.setText("Deleted!");
        App.lb19.setTextFill(Color.BLACK);

        double HEIGHT_PER_HOUR = App.timesBox.getHeight() / 11.0;
        App.sunday.getChildren().clear();
        App.monday.getChildren().clear();
        App.wednesday.getChildren().clear();
        App.tuesday.getChildren().clear();
        App.thursday.getChildren().clear();

        if (Days.equals("UTR")) {
            App.SundaySections.remove(toRemovSection);
            App.TusedaySections.remove(toRemovSection);
            App.ThursdaySections.remove(toRemovSection);
        }

        if (Days.equals("UT")) {
            App.SundaySections.remove(toRemovSection);
            App.TusedaySections.remove(toRemovSection);

        }

        if (Days.equals("MW")) {
            App.WednesdaySections.remove(toRemovSection);
            App.MondaySections.remove(toRemovSection);

        }
        if (Days.equals("U")) {
            App.SundaySections.remove(toRemovSection);

        }
        if (Days.equals("M")) {
            App.MondaySections.remove(toRemovSection);

        }
        if (Days.equals("T")) {
            App.TusedaySections.remove(toRemovSection);

        }
        if (Days.equals("W")) {
            App.WednesdaySections.remove(toRemovSection);

        }
        if (Days.equals("R")) {
            App.ThursdaySections.remove(toRemovSection);

        }
        for (Section adSection : App.ScheduleSections) {
            Days = adSection.getDays();
            int index = (int) (Math.random() * 15);
            for (int j = 0; j < Days.length(); j++) {
                int[] time = adSection.period();
                double start = (((time[0] - 420.0) / 60.0) * HEIGHT_PER_HOUR);
                double height = ((time[1] - time[0]) / 60.0) * HEIGHT_PER_HOUR;
                Button section = new Button(adSection.toString2());
                section.setStyle(colors[index]);

                section.setOnAction(new ButtonHandler());
                section.setWrapText(true);
                section.setPrefSize(130, height);
                Character temp = (Days.charAt(j));
                if (String.valueOf(temp).equals("U")) {

                    App.sunday.getChildren().add(section);
                    section.setTranslateY(start - App.addedHeight[0]);
                    App.addedHeight[0] += height;
                }
                if (String.valueOf(temp).equals("M")) {

                    App.monday.getChildren().add(section);
                    section.setTranslateY(start - App.addedHeight[1]);
                    App.addedHeight[1] += height;

                }
                if (String.valueOf(temp).equals("T")) {

                    App.tuesday.getChildren().add(section);
                    section.setTranslateY(start - App.addedHeight[2]);
                    App.addedHeight[2] += height;
                }
                if (String.valueOf(temp).equals("W")) {

                    App.wednesday.getChildren().add(section);
                    section.setTranslateY(start - App.addedHeight[3]);
                    App.addedHeight[3] += height;
                }
                if (String.valueOf(temp).equals("R")) {

                    App.thursday.getChildren().add(section);
                    section.setTranslateY(start - App.addedHeight[4]);
                    App.addedHeight[4] += height;
                }

            }

        }

    }

    @Override
    public void handle(ActionEvent e) {
        String sectionString = ((Button) (e.getSource())).getText();
        Section sectionChosed = null;

        for (Section f : App.getBasket()) {

            if (f.toString2().equals(sectionString)) {

                sectionChosed = f;
            }
        }
        removeSection(sectionChosed);
    }
}
class ButtonHandler2 implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent e) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("array.dat"));
            output.writeObject(App.ScheduleSections);
            App.save.setText("Saved");

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

}