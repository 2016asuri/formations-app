/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package senior.research.pkg1;

import java.awt.Point;;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.shape.*;
import javafx.animation.*;
import javafx.animation.PathTransition.OrientationType;
import javafx.scene.paint.Color;

/**
 *
 * @author anany
 */
public class FXMLDocumentController implements Initializable {
    
    private ArrayList<ArrayList<Dancer>> formations = new ArrayList<>();
    private ArrayList<Dancer> dancers = new ArrayList<>();
    private Dancer currentDancer;
    
    //private Group dancerImages = new Group();
    
    @FXML
    private Button front;
    @FXML
    private Button back;
    @FXML
    private Button right;
    @FXML
    private Button left;
    @FXML
    private Button done;
    @FXML
    private Label instructions;
    @FXML 
    private AnchorPane anchor;
    @FXML 
    private Node hiddenStage;
    @FXML
    private Label lastAction;
    
    @FXML
    private void handleNewFormation(ActionEvent event)
    {
        lastAction.setText("No dancers on stage.");
        if(done.isDisabled())
        {
            hiddenStage.setVisible(false);
            front.setDisable(false);
            back.setDisable(false);
            left.setDisable(false);
            right.setDisable(false);
            done.setDisable(false);
        }
        else
            nextFormation();
        //System.out.println(anchor.getChildren().size());
    }
    @FXML
    private void handleFrontDancerAdd(ActionEvent event) 
    {
        //dancer info
        TextInputDialog dialog = new TextInputDialog("name");
        dialog.setTitle("Dancer: front");
        dialog.setHeaderText("");
        dialog.setContentText("Name of dancer: ");
        Optional<String> result = dialog.showAndWait();
        Dancer d = new Dancer(result.get(), 0);
        currentDancer = d;
        //System.out.println(currentDancer);
        instructions.setVisible(true);
    }
    @FXML
    private void handleBackDancerAdd(ActionEvent event) 
    {
        TextInputDialog dialog = new TextInputDialog("name");
        dialog.setTitle("Dancer: back");
        dialog.setHeaderText("");
        dialog.setContentText("Name of dancer: ");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        Dancer d = new Dancer(result.get(), 1);
        currentDancer = d;
    }
    @FXML
    private void handleRightDancerAdd(ActionEvent event) 
    {
        TextInputDialog dialog = new TextInputDialog("name");
        dialog.setTitle("Dancer: right");
        //dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Name of dancer: ");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        Dancer d = new Dancer(result.get(), 3);
        currentDancer = d;
//        System.out.print("currentDancer = " + d);
    }
    @FXML
    private void handleLeftDancerAdd(ActionEvent event) 
    {
        TextInputDialog dialog = new TextInputDialog("name");
        dialog.setTitle("Dancer: left");
        dialog.setHeaderText("");
        dialog.setContentText("Name of dancer: ");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        Dancer d = new Dancer(result.get(), 2);
        currentDancer = d;
    }
    @FXML
    private void nextFormation() 
    {
        int numDancers = dancers.size();
        formations.add(dancers);
        dancers = new ArrayList<Dancer>();
        lastAction.setText("New formation made.");
        
        //System.out.println(numDancers);
        int endIndex = anchor.getChildren().size();
        //int startIndex = endIndex - numDancers - 1;
        int startIndex = 55;
        //System.out.println(startIndex + " " + endIndex);
        anchor.getChildren().remove(startIndex, endIndex);
    }
    @FXML
    private void handleDoneButtonAction(ActionEvent event) 
    {
        formations.add(dancers);
        //anchor.getChildren().remove(56, anchor.getChildren().size());
        //nextFormation();
        
        //hiddenStage.setVisible(true);
//        System.out.println(formations.get(0));
//        System.out.println(formations.get(1));
        
        makeAnimations();
    }
    private void makeAnimations()
    {
        System.out.println(formations);
        ArrayList<Path> paths = new ArrayList<>();
//        if(formations.size()==1)
//        {
//            ArrayList<Dancer> f1 = formations.get(0);
//            ArrayList<Dancer> 
//        }
        for(int i=0; i<formations.size()-1; i++)
        {
            ArrayList<Dancer> f1 = formations.get(i);
            ArrayList<Dancer> f2 = formations.get(i+1);
            ShortestPath pathFinder = new ShortestPath(f1, f2);
//            System.out.println(f1);
//            System.out.println(f2);
            
           pathFinder.findPaths();
           System.out.println(pathFinder.getPaths());
           paths = pathFinder.getPaths();
        }
   
        ParallelTransition parallel = new ParallelTransition();
        for(int i=0; i<paths.size(); i++)
        {
            int dancerIndex = 55+2*i;
            ImageView dancerImage = (ImageView)anchor.getChildren().get(dancerIndex);
            //Text dancerLabel = new Text(formations.get(0).get(i).getName());
            //System.out.println(formations.get(0).get(i).getName());
            Text dancerLabel = (Text)anchor.getChildren().get(dancerIndex+1);
            double currX = paths.get(i).getStep(0).getX()*25+17.5+12.5;
            double currY = paths.get(i).getStep(0).getY()*25+7.5+12.5;
            dancerImage.setX(currX);
            dancerImage.setY(currY);
            dancerLabel.setX(currX);
            dancerLabel.setY(currY-17.5);
            //anchor.getChildren().add(dancerLabel);
            javafx.scene.shape.Path dancerPath = new javafx.scene.shape.Path();
            javafx.scene.shape.Path labelPath = new javafx.scene.shape.Path();
            dancerPath.getElements().add(new MoveTo(currX, currY));
            labelPath.getElements().add(new MoveTo(currX, currY-17.5));
            for(int j=0; j<paths.get(i).getLength()-1; j++)
            {
                double nextX = paths.get(i).getStep(j+1).getX()*25+17.5+12.5;
                double nextY = paths.get(i).getStep(j+1).getY()*25+7.5+12.5;
            
                dancerPath.getElements().add(new LineTo(nextX, nextY));
                labelPath.getElements().add(new LineTo(nextX, nextY-17.5));
            }
            //double lastX = paths.get(i).getStep(paths.get(i).getLength()-1).getX()*25+17.5+12.5;
            //double lastY = paths.get(i).getStep(paths.get(i).getLength()-1).getY()*25+7.5+12.5;
            //dancerPath.getElements().add(new LineTo(lastX, lastY));
            //labelPath.getElements().add(new LineTo(lastX, lastY-17.5));
            //System.out.println(animationPath.getElements());
            PathTransition pt = new PathTransition();
            pt.setNode(dancerImage);
            pt.setPath(dancerPath);
            pt.setDuration(Duration.seconds(5));
            pt.setOrientation(OrientationType.NONE);
            pt.setCycleCount(1);
            parallel.getChildren().add(pt);
            
            PathTransition ptLabel = new PathTransition();
            ptLabel.setNode(dancerLabel);
            ptLabel.setPath(labelPath);
            ptLabel.setDuration(Duration.seconds(5));
            ptLabel.setOrientation(OrientationType.NONE);
            ptLabel.setCycleCount(1);
            parallel.getChildren().add(ptLabel);
        }
        parallel.play();
        System.out.println("played");

    }
    @FXML
    private void handleDancerPos(MouseEvent event)
    {   
        double x = event.getX();
        double y = event.getY();
        double scaleX = x/25.0;
        double scaleY = y/25.0;
        //System.out.println("current dancer: " + currentDancer);
        
        if(currentDancer == null)
            return;
        currentDancer.setPos(new Point((int)Math.round(scaleX), (int) Math.round(scaleY)));
        
        Text name = new Text(currentDancer.getName());
        name.setFill(Color.WHITE);
        ImageView i = getDancerImage();
        if(i == null)
            return;
        i.setFitHeight(25);
        i.setFitWidth(25);
        i.setX(x+17.5);
        i.setY(y+7.5);
        if(currentDancer.getName().length() == 2)
            name.setX(x+25);
        else
            name.setX(x+27);
        name.setY(y+7.5);
        i.setVisible(true);
        anchor.getChildren().add(i);
        anchor.getChildren().add(name);
        lastAction.setText("Dancer " + currentDancer.getName() + " added to stage at (" + (int)Math.round(scaleX) + ", " + (int)Math.round(scaleY) + ").");
        dancers.add(currentDancer);
        //System.out.println(dancers.size());
        currentDancer = null;
        instructions.setVisible(false);
    }
    private ImageView getDancerImage()
    {
        if (currentDancer == null)
            return null;
        String side = currentDancer.getSideString();
        String url = "/images/" + side + "1.png";
        Image img = new Image(url);
        ImageView i = new ImageView(img);
        return i; 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        lastAction.setText("Welcome! Click \"New formation\" to begin!");
    }
    
}
