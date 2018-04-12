	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.List;

	import javafx.application.Application;
	import javafx.beans.value.ChangeListener;
	import javafx.beans.value.ObservableValue;
	import javafx.collections.ListChangeListener;
	import javafx.collections.ObservableList;
	import javafx.event.ActionEvent;
	import javafx.event.Event;
	import javafx.event.EventHandler;
	import javafx.geometry.Insets;
	import javafx.scene.Group;
	import javafx.scene.Scene;
	import javafx.scene.control.Button;
	import javafx.scene.control.Hyperlink;
	import javafx.scene.control.TextField;
	import javafx.scene.input.KeyCode;
	import javafx.scene.input.KeyEvent;
	import javafx.scene.input.MouseEvent;
	import javafx.scene.layout.BorderPane;
	import javafx.scene.layout.HBox;
	import javafx.scene.layout.Priority;
	import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
	import javafx.scene.web.WebEvent;
	import javafx.scene.web.WebView;
	import javafx.scene.web.WebHistory;
	import javafx.scene.web.WebHistory.Entry;
	import javafx.stage.Stage;
	import javafx.concurrent.Worker.State;
	import javafx.concurrent.Worker;
	import javafx.scene.layout.HBox;
	import javafx.scene.text.Text;

	/**
	 * Web Browser.java
	 * Simple web browser program
	 * 
	 * CS 1131
	 * Assignment #6: Web Browser
	 * 
	 * @author Javen Zamojcin
	 */
	public class Program100 extends Application {
		
		// INSTANCE VARIABLES

		private Stage stage = new Stage();
		private BorderPane borderPane = new BorderPane();
		private WebView view = new WebView();
		private WebEngine webEngine = view.getEngine();
		private TextField statusbar = new TextField();

		// HELPER METHODS

		private String getParameter( int index ) {
			Parameters params = getParameters();
			List<String> parameters = params.getRaw();
			return !parameters.isEmpty() ? parameters.get(index) : "";
		}

		/**
		 * Creates a WebView which handles mouse and some keyboard events, and
		 * manages scrolling automatically, so there's no need to put it into a ScrollPane.
		 * The associated WebEngine is created automatically at construction time.
		 *
		 * @return browser - a WebView container for the WebEngine.
		 */
		private WebView makeHtmlView( ) {
			view = new WebView();
			webEngine = view.getEngine();
			return view;
		}

		/**
		 * Generates the status bar layout and text field.
		 *
		 * @return statusbarPane - the HBox layout that contains the statusbar.
		 */
		private HBox makeStatusBar( ) {
			HBox statusbarPane = new HBox();
			statusbarPane.setPadding(new Insets(5, 4, 5, 4));
			statusbarPane.setSpacing(10);
			statusbarPane.setStyle("-fx-background-color: #336699;");
			HBox.setHgrow(statusbar, Priority.ALWAYS);
			statusbarPane.getChildren().addAll(statusbar);
			return statusbarPane;
			
			//Wasn't displaying text in statusbar when I used this; ignoring.
		}

		// REQUIRED METHODS
		/**
		 * The main entry point for all JavaFX applications. The start method is
		 * called after the init method has returned, and after the system is ready
		 * for the application to begin running.
		 *
		 * NOTE: This method is called on the JavaFX Application Thread.
		 *
		 * @param primaryStage - the primary stage for this application, onto which
		 * the application scene can be set.
		 */
		
		@Override
		public void start(Stage stage) {
			
			Program100 obj = new Program100();
			String helpPage = "<html><body><b>Javen Zamojcin </br> CS 1131 </br> Lab Section: L02 </br> Web Browser Program </br></br>"
					+ " Enter full website URL in the address bar at the top, then press the 'Enter' key to navigate websites. </br>"
					+ " The status bar at the bottom displays URLs of links your cursor hovers over. </br>"
					+ "Use the back / forward buttons in the top left corner to navigate through previously loaded webpages. </br>"
					+ "Press the '?' button in the top right to display this page.  </b></body></html>";
			
			//Address Bar
			TextField addressBar = new TextField();
			addressBar.setPrefSize(500, 30);
			
			addressBar.setOnAction( event -> {
				
				webEngine.load(addressBar.getText());
				
			});
			
			webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
			     @Override
			     public void changed(ObservableValue ov, State oldState, State newState) {

			           if (newState == Worker.State.SUCCEEDED) {
			                 addressBar.setText(webEngine.getLocation());
			           }
			     }
			});
			
			//Buttons
			Button forwardBtn = new Button();
			forwardBtn.setText(" --> ");
			forwardBtn.setPrefSize(50, 30);
			forwardBtn.setOnAction(event -> {
				
				webEngine.getHistory().go(1);
				
			});
			
			Button backBtn = new Button();
			backBtn.setText(" <-- ");
			backBtn.setPrefSize(50, 30);
			backBtn.setOnAction(event -> {
				
				webEngine.getHistory().go(-1);
				
			});

			Button helpBtn = new Button();
			helpBtn.setText(" ? ");
			helpBtn.setPrefSize(50, 30);
			helpBtn.setOnAction(event -> {
				
				webEngine.loadContent(helpPage);
				
			});
			
			//AddressBar HBox
			HBox addressBody = new HBox(25);
			addressBody.setPadding(new Insets(15, 12, 15, 12 ));
			addressBody.setStyle("-fx-background-color: #336699;");
			addressBody.getChildren().add(backBtn);
			addressBody.getChildren().add(forwardBtn);
			addressBody.getChildren().add(addressBar);
			addressBody.getChildren().add(helpBtn);
			
			//StatusBar
			webEngine.setOnStatusChanged( event -> {
				
				statusbar.setText(event.getData());
				
			});
			
		
			//StatusBar HBox
			HBox statusBox = new HBox();
			statusBox.setPadding(new Insets(5, 4, 5, 4));
			statusBox.setSpacing(10);
			statusBox.setStyle("-fx-background-color: #336699;");
			HBox.setHgrow(statusbar, Priority.ALWAYS);
			statusBox.getChildren().addAll(statusbar);
			
			//HTML Viewer
			webEngine.load(helpPage);
			
			if ( getParameter(0) != "" ) {
				
				webEngine.load(getParameter(0));
				
			}
				
		
			//Border Pane
			borderPane.setTop(addressBody);
			borderPane.setBottom(statusBox);
			borderPane.setCenter(view);
			
			//Scene & Stage
			Scene scene = new Scene(borderPane, 800, 600);
			stage.setScene(scene);

			webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
			     @Override
			     public void changed(ObservableValue ov, State oldState, State newState) {

			           if (newState == Worker.State.SUCCEEDED) {
			        	   
			        	   if (webEngine.getTitle() != null) {
			        		   
			        	   stage.setTitle(webEngine.getTitle());
			        	   
			        	   } else if (webEngine.getTitle() == null ) {
			        		   
			        		   stage.setTitle(webEngine.getLocation());
			        		   
			        	   } else {
			        		   
			        		   stage.setTitle("");
			        		   
			        	   }
			           }
			     }
			});
			
			stage.show();
		
		}

		/**
		 * The main( ) method is ignored in JavaFX applications.
		 * main( ) serves only as fallback in case the application is launched
		 * as a regular Java application, e.g., in IDEs with limited FX
		 * support.
		 *
		 * @param args the command line arguments
		 */
		public static void main(String[] args) {
			launch(args);
			
		}
	}

