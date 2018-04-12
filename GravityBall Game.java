import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * GravityBall Game.java
 * Simple platform game where the objective is to keep the ball from falling off the screen.
 * 
 * CS 1131
 * Assignment #7: Minigame
 * Last Date Modified: 11/1/2017
 * 
 * @author Javen Zamojcin
 */

public class GravityBallMiniGame extends Application {
	
	int count = 0;
	int difficulty = 0;
	boolean gameEnd = false;
	
    @Override
    public void start( Stage stage ) {
    	
        Pane canvas = new Pane( );
        
        Sphere ball = new Sphere( 15, 15 );
        ball.relocate( 500, 100 );
        canvas.getChildren( ).add( ball );

        Rectangle paddle = new Rectangle();
        paddle.setX( 500 );
        paddle.setY( 700 );
        paddle.setWidth(150);
        paddle.setHeight(15);
        paddle.setFill(Color.BLUE);
        canvas.getChildren( ).add( paddle );
        
        Text instructions = new Text("Use arrow keys \n to move");
        instructions.setFont(Font.font("Verdana", 65));
        instructions.setFill(Color.DARKGOLDENROD);
        instructions.setX(600);
        instructions.setY(100);
        canvas.getChildren().add(instructions);
        
		Text countBox = new Text("Score: " + count);
        countBox.setFont(Font.font("Verdana", 80));
        countBox.setFill(Color.BEIGE);
        countBox.setX( 50 );
        countBox.setY( 100 );
        canvas.getChildren().add(countBox);
        
        Text endText = new Text();
        endText.setFont(Font.font("Verdana", 80));
        endText.setFill(Color.BLACK);
        endText.setY( 400 );
        endText.setX( 50 );
        canvas.getChildren().add(endText);
        
        Scene scene1 = new Scene( canvas, 300, 300, Color.BLACK );
        
        stage.setTitle( "Gravity Ball" );
        stage.setScene( scene1 );
        stage.setFullScreen(true);
        stage.show( );
        
        
        
        Timeline timeline = new Timeline( new KeyFrame( Duration.millis( 20 ),
                new EventHandler< ActionEvent >( ) {
                    double dx = 1; //Step on x or velocity
                    double dy = 1; //Step on y

                    @Override
                    public void handle( ActionEvent t ) {
                    	
                        //move the ball
                        ball.setLayoutX( ball.getLayoutX( ) + dx );
                        ball.setLayoutY( ball.getLayoutY( ) + dy );
                        dy++;

                        Bounds bounds = canvas.getBoundsInLocal( );
                        
                        //If the ball reaches the left or right border make the step negative
                        if ( ball.getLayoutX( ) <= ( bounds.getMinX( ) + ball.getRadius( ) ) ||
                                ball.getLayoutX( ) >= ( bounds.getMaxX( ) - ball.getRadius( ) ) ) {
                        	
                            dx = -dx;
                            
                        }
                        
                        //If the ball reaches the bottom or top border make the step negative
                        if (  ball.getLayoutY( ) <= ( bounds.getMinY( ) + ball.getRadius( ) ) )
                        
                        {
                        	
                            dy--;
                            dy = -dy;
                            
                        }
                        
                        // Detecting if the ball hits the paddle
                        if (ball.getLayoutX() > paddle.getX() && ball.getLayoutX() < paddle.getX() + paddle.getWidth()) {
                        	
                            if ((ball.getLayoutY() + ball.getRadius() >= (paddle.getY() - ball.getRadius()))) {
                            	
                                dy--;
                                dy = -dy;
                                
                                dx += 1.5;
                               
                                
                                count++;
                                countBox.setText("Score: " + count);
                                
                           }
                        }   
                        
                        //Detecting if ball falls out of bounds
                        if ( ball.getLayoutY() >= 720 ) {
                        	
                        	dy = 0;
                        	dx = 0;
                        	
                        	ball.setLayoutX(0);
                        	ball.setLayoutY(0);
                        	ball.setOpacity(0);
                        	
                        	gameEnd = true;
                        	
                        	countBox.setFill(Color.BLACK);
                        	endText.setText("Final Score: " + count);
                        	endText.setFill(Color.CRIMSON);
                        	
                        	instructions.setText("Press Q to close");
                        	instructions.setOpacity(100);
                        	
                        }
                    }
                }));
        
        	timeline.setCycleCount( Timeline.INDEFINITE );
        
        	scene1.setOnKeyPressed( e -> {
        	
        	   timeline.play();
        	   
        	   switch ( e.getCode( ) ) {
        	       case LEFT:
        	    	   if ( gameEnd == false) {	
        	    		   
        	    		   if ( paddle.getX() < 0) {
        	    			   break;
        	    		   }
        	    		  
        	    		   paddle.setX( paddle.getX( ) - 60 ) ;
        	    		   break;
        	    	   }
        	    	   
        	    	   break;
        	    	   
        	       case RIGHT:
        	    	   
        	    	 if ( gameEnd == false ) {
        	    		  
        	    	   	if ( paddle.getX() > 1200) {
        	    	   		break;
        	    	   	}
        	    	   	
        	    	   	paddle.setX( paddle.getX( ) + 60 ) ;
        	           	break;
        	    	  }
        	    	 
        	    	 break;
        	    	 
        	       case Q:
        	    	   
        	    	   stage.close();
        	    	   
        	   } 
        });
    }

    
    public static void main( String[] args ) {
        launch( );
    }
}