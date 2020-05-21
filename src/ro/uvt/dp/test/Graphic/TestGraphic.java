/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uvt.dp.test.Graphic;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ro.uvt.dp.bank.Bank;
import ro.uvt.dp.client.Client;
import ro.uvt.dp.client.account.Account;
import ro.uvt.dp.client.account.AccountEUR;

/**
 *
 * @author Ricardo Belinha
 */
public class TestGraphic extends Application {

    private AudioClip bSound = new AudioClip(this.getClass().getResource("Sounds/buttonSound.mp3").toExternalForm());
    private Bank bcr;
    private Stage stage;

    private static TestGraphic instance;

    public TestGraphic() {
        instance = this;
    }

    public static TestGraphic getInstance() {
        return instance;
    }

    public Bank getLoggedBank() {
        return bcr;
    }

    private Stage getStage() {
        return stage;
    }

    private void switchScene(Scene scene) {
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            switchScene(getLoginScene());
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(TestGraphic.class.getName()).log(Level.SEVERE, null, ex);
        }
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Scene getLoginScene() {
        getStage().setTitle("Bank Management System");
        VBox pane = new VBox();
        GridPane grid = new GridPane();
        // SIGNED BY ME
        Label myName = new Label("created by Ricardo Belinha ");
        myName.setStyle("-fx-font-weight: bold");
        Scene scene = new Scene(pane, 400, 320);
        grid.setMaxSize(400, 300);
        grid.setMinSize(400, 300);
        grid.setPrefSize(400, 300);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        pane.setMaxSize(400, 320);
        pane.setMinSize(400, 320);
        pane.setPrefSize(400, 320);
        pane.setAlignment(Pos.CENTER_RIGHT);
        pane.getChildren().addAll(grid, myName);
        // Avatar
        ImageView iv1 = new ImageView(new Image(getClass().getResourceAsStream("Images/login.png"), 100, 100, true, false));
        grid.add(iv1, 0, 1);
        GridPane.setHalignment(iv1, HPos.CENTER);

        // EMPTY ROW
        final Pane spring = new Pane();
        spring.setPrefHeight(25);
        grid.add(spring, 0, 2);

        // BANK CODE
        Label bankCode = new Label("Bank Code: ");
        bankCode.setStyle("-fx-font-weight: bold");
        grid.add(bankCode, 0, 3);
        TextField bankCodeTextField = new TextField();
        bankCodeTextField.setPrefWidth(200);
        grid.add(bankCodeTextField, 0, 4);

        // EMPTY ROW
        final Pane spring1 = new Pane();
        spring1.setPrefHeight(25);
        grid.add(spring1, 0, 5);

        Button button = new MyButton("Connect");

        button.setOnMousePressed((MouseEvent event) -> {
            bSound.play();
            if (!bankCodeTextField.getText().isEmpty()) {
                bcr = new Bank(bankCodeTextField.getText());
                switchScene(getManageScene());
            }
        });
        grid.add(button, 0, 6);
        GridPane.setHalignment(button, HPos.CENTER);

        grid.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        return scene;
    }

    private Scene getManageScene() {
        getStage().setTitle("Bank Management System - Bank Side");
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 500, 400);
        grid.setMaxSize(500, 400);
        grid.setMinSize(500, 400);
        grid.setPrefSize(500, 400);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));

        // CONSTRUCT THIS PANE
        // Avatar
        ImageView iv1 = new ImageView(new Image(getClass().getResourceAsStream("Images/bank.png"), 100, 100, true, false));
        grid.add(iv1, 0, 1);
        GridPane.setHalignment(iv1, HPos.CENTER);
        GridPane.setColumnSpan(iv1, 2);

        // EMPTY ROW
        final Pane spring = new Pane();
        spring.setPrefHeight(25);
        grid.add(spring, 0, 2);

        // INFORMATIONS
        Label info = new Label("Bank information");
        info.setFont(new Font("Arial", 20));
        info.setStyle("-fx-font-weight: bold");
        grid.add(info, 0, 3);
        GridPane.setHalignment(info, HPos.CENTER);
        GridPane.setColumnSpan(info, 2);

        // BANK CODE
        Label bankCode = new Label("Bank Code: ");
        bankCode.setStyle("-fx-font-weight: bold");
        grid.add(bankCode, 0, 4);
        GridPane.setHalignment(bankCode, HPos.RIGHT);
        Label bankCodeShow = new Label(bcr.getBankCode());
        grid.add(bankCodeShow, 1, 4);
        GridPane.setHalignment(bankCodeShow, HPos.LEFT);

        // EMPTY ROW
        final Pane spring1 = new Pane();
        spring1.setPrefHeight(25);
        grid.add(spring1, 0, 5);

        // Management tools
        Label tools = new Label("Management tools");
        tools.setFont(new Font("Arial", 20));
        tools.setStyle("-fx-font-weight: bold");
        grid.add(tools, 0, 6);
        GridPane.setHalignment(tools, HPos.CENTER);
        GridPane.setColumnSpan(tools, 2);

        // ADD CLIENT
        Button addClient = new MyButton("Add Client");
        addClient.setOnMousePressed((MouseEvent event) -> {
            bSound.play();
            switchScene(getAddingClientScene());
        });
        grid.add(addClient, 0, 7);
        GridPane.setHalignment(addClient, HPos.CENTER);
        // ALL CLIENTS
        Button allClients = new MyButton("List All Clients");
        allClients.setOnMousePressed((MouseEvent event) -> {
            bSound.play();
            switchScene(getListAllClientsScene());
        });
        grid.add(allClients, 1, 7);
        GridPane.setHalignment(allClients, HPos.CENTER);
        // GET CLIENT
        Button getClient = new MyButton("Show Client");
        getClient.setOnMousePressed((MouseEvent event) -> {
            bSound.play();
            Scene newScene;
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Bank Management System - Insert a name");
            dialog.setHeaderText("After you type the client's name, the information about him gonna be appears.");
            dialog.setContentText("Please enter client's name:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    newScene = getFindClientScene(result.get());
                } catch (Exception ex) {
                    newScene = getManageScene();
                }
                switchScene(newScene);
            }

        });
        grid.add(getClient, 0, 8);
        GridPane.setHalignment(getClient, HPos.CENTER);
        // NEW ACCOUNT
        Button closeAccount = new MyButton("Close account");
        closeAccount.setOnMousePressed((MouseEvent event) -> {
            bSound.play();
            switchScene(getCloseAnAccount());
        });
        grid.add(closeAccount, 1, 8);
        GridPane.setHalignment(closeAccount, HPos.CENTER);
        return scene;
    }

    private Scene getAddingClientScene() {
        getStage().setTitle("Bank Management System - New client");
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 500, 400);
        grid.setMaxSize(500, 400);
        grid.setMinSize(500, 400);
        grid.setPrefSize(500, 400);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));

        // CONSTRUCT THIS PANE
        // Avatar
        ImageView iv1 = new ImageView(new Image(getClass().getResourceAsStream("Images/avatar.png"), 100, 100, true, false));
        grid.add(iv1, 0, 1);
        GridPane.setHalignment(iv1, HPos.CENTER);
        GridPane.setColumnSpan(iv1, 2);

        // EMPTY ROW
        final Pane spring = new Pane();
        spring.setPrefHeight(25);
        grid.add(spring, 0, 2);

        // Name
        // Label
        Label name = new Label("Name: ");
        name.setStyle("-fx-font-weight: bold");
        grid.add(name, 0, 3);
        GridPane.setHalignment(name, HPos.RIGHT);
        // Text Field
        TextField nameTF = new TextField();
        nameTF.setPrefWidth(200);
        grid.add(nameTF, 1, 3);
        GridPane.setHalignment(nameTF, HPos.LEFT);
        // Account
        // Label
        Label accountType = new Label("Account Type: ");
        accountType.setStyle("-fx-font-weight: bold");
        grid.add(accountType, 0, 4);
        GridPane.setHalignment(accountType, HPos.RIGHT);
        // Spinner
        final Spinner<String> spinner = new Spinner<>();
        ObservableList<String> accounts = FXCollections.observableArrayList("EUR", "RON");
        // Value factory.
        SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(accounts);
        // Default value
        valueFactory.setValue("EUR");
        spinner.setValueFactory(valueFactory);
        spinner.setPrefWidth(80);
        grid.add(spinner, 1, 4);
        GridPane.setHalignment(spinner, HPos.LEFT);
        // Suma
        Label suma = new Label("Value: ");
        suma.setStyle("-fx-font-weight: bold");
        grid.add(suma, 0, 5);
        GridPane.setHalignment(suma, HPos.RIGHT);
        final Spinner<Double> spinnerSuma = new Spinner<>();
        final double initialValue = 20.0;
        // Value factory.
        SpinnerValueFactory<Double> valueFactorySpinnerSuma = new SpinnerValueFactory.DoubleSpinnerValueFactory(20, 50000, initialValue, 20);
        spinnerSuma.setValueFactory(valueFactorySpinnerSuma);
        spinnerSuma.setPrefWidth(100);
        grid.add(spinnerSuma, 1, 5);
        GridPane.setHalignment(spinnerSuma, HPos.LEFT);

        // EMPTY ROW
        final Pane spring1 = new Pane();
        spring1.setPrefHeight(25);
        grid.add(spring1, 0, 6);

        // Button Add Client
        Button addClient = new MyButton("Add Client");
        addClient.setAlignment(Pos.CENTER);
        addClient.setOnMousePressed((MouseEvent event) -> {
            bSound.play();
            try {
                if (spinner.getValue().compareTo("EUR") == 0) {
                    Client clients = new Client.ClientBuilder(nameTF.getText(), Account.TYPE.EUR, spinnerSuma.getValue()).build();
                    // TODO IF I WANT TO ADD MORE ACCOUNTS HERE JUST TO DEBUG
                    //clients.addAccount(Account.TYPE.EUR, 5);
                    bcr.addClient(clients);

                } else {
                    bcr.addClient(new Client.ClientBuilder(nameTF.getText(), Account.TYPE.RON, spinnerSuma.getValue()).build());
                }
            } catch (Exception ex) {
                Logger.getLogger(TestGraphic.class.getName()).log(Level.SEVERE, null, ex);
            }
            new Alert(Alert.AlertType.INFORMATION, nameTF.getText() + " was added to the client list.").showAndWait();
            switchScene(getManageScene());
        });

        grid.add(addClient, 0, 7);
        GridPane.setHalignment(addClient, HPos.CENTER);
        // Button Return
        Button returnMenu = new MyButton("Return to the menu");
        returnMenu.setAlignment(Pos.CENTER);
        returnMenu.setOnMousePressed((MouseEvent event) -> {
            bSound.play();
            switchScene(getManageScene());
        });

        grid.add(returnMenu, 1, 7);
        GridPane.setHalignment(returnMenu, HPos.CENTER);
        return scene;
    }

    private Scene getFindClientScene(String clientName) throws Exception {
        getStage().setTitle("Bank Management System - " + clientName);
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 500, 400);
        grid.setMaxSize(500, 400);
        grid.setMinSize(500, 400);
        grid.setPrefSize(500, 400);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));

        // CONSTRUCT THIS PANE
        Client client = bcr.getClient(clientName);
        if (client == null) {
            throw new Exception();
        }

        // CONSTRUCT THIS PANE
        // Avatar
        ImageView iv1 = new ImageView(new Image(getClass().getResourceAsStream("Images/avatar.png"), 100, 100, true, false));
        grid.add(iv1, 0, 1);
        GridPane.setHalignment(iv1, HPos.CENTER);
        GridPane.setColumnSpan(iv1, 2);

        // EMPTY ROW
        final Pane spring = new Pane();
        spring.setPrefHeight(25);
        grid.add(spring, 0, 2);

        // Name
        // Label
        Label name = new Label("Name: ");
        name.setStyle("-fx-font-weight: bold");
        grid.add(name, 0, 3);
        GridPane.setHalignment(name, HPos.RIGHT);
        // Text Field
        TextField nameTF = new TextField(client.getName());
        nameTF.setPrefWidth(200);
        nameTF.setDisable(true);
        grid.add(nameTF, 1, 3);
        GridPane.setHalignment(nameTF, HPos.LEFT);

        // Accounts
        // Label
        Label accL = new Label("Accounts: ");
        accL.setStyle("-fx-font-weight: bold");
        grid.add(accL, 0, 4);
        GridPane.setHalignment(accL, HPos.RIGHT);
        // Account List
        ListView<String> list = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        client.getAccounts().forEach((acc) -> {
            items.add(acc.getAccountNumber());
        });
        list.setItems(items);
        int limite = 3;
        if (items.size() <= limite) {
            list.setPrefHeight(items.size() * (list.getFixedCellSize() + 32));
        } else {
            list.setPrefHeight(limite * (list.getFixedCellSize() + 32));
        }
        //list.setDisable(true);
        grid.add(list, 1, 4);
        GridPane.setHalignment(list, HPos.LEFT);

        // EMPTY ROW
        final Pane spring1 = new Pane();
        spring1.setPrefHeight(25);
        grid.add(spring1, 0, 5);

        // Button Show Account
        Button showAccount = new MyButton("Show Account");
        showAccount.setOnMousePressed((MouseEvent event) -> {
            bSound.play();
            if (!list.getSelectionModel().getSelectedItem().isEmpty()) {
                switchScene(getShowAccountScene(list.getSelectionModel().getSelectedItem(), client.getName()));
            }
        });
        grid.add(showAccount, 0, 6);
        GridPane.setHalignment(showAccount, HPos.CENTER);

        // Button Return
        Button returnMenu = new MyButton("Return to the menu");
        returnMenu.setOnMousePressed((MouseEvent event) -> {
            bSound.play();
            switchScene(getManageScene());
        });
        grid.add(returnMenu, 1, 6);
        GridPane.setHalignment(returnMenu, HPos.CENTER);
        return scene;
    }

    private Scene getCloseAnAccount() {
        getStage().setTitle("Bank Management System - Close an account");
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 550, 300);
        grid.setMaxSize(550, 300);
        grid.setMinSize(550, 300);
        grid.setPrefSize(550, 300);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));

        // CONSTRUCT THIS PANE
        // Avatar
        ImageView iv1 = new ImageView(new Image(getClass().getResourceAsStream("Images/avatar.png"), 100, 100, true, false));
        grid.add(iv1, 0, 1);
        GridPane.setHalignment(iv1, HPos.CENTER);
        GridPane.setColumnSpan(iv1, 2);

        // EMPTY ROW
        final Pane spring = new Pane();
        spring.setPrefHeight(25);
        grid.add(spring, 0, 2);

        // Name
        // Label
        Label name = new Label("Client's name: ");
        name.setStyle("-fx-font-weight: bold");
        grid.add(name, 0, 3);
        GridPane.setHalignment(name, HPos.RIGHT);
        // Text Field
        TextField nameTF = new TextField();
        nameTF.setPrefWidth(200);
        grid.add(nameTF, 1, 3);
        GridPane.setHalignment(nameTF, HPos.LEFT);
        // Account
        // Label
        Label ibanLabel = new Label("Account IBAN: ");
        ibanLabel.setStyle("-fx-font-weight: bold");
        grid.add(ibanLabel, 0, 4);
        GridPane.setHalignment(ibanLabel, HPos.RIGHT);
        // Text Field
        TextField iban = new TextField();
        iban.setPrefWidth(200);
        grid.add(iban, 1, 4);
        GridPane.setHalignment(iban, HPos.LEFT);

        // EMPTY ROW
        final Pane spring2 = new Pane();
        spring2.setPrefHeight(25);
        grid.add(spring2, 0, 5);

        // Button Close Account
        Button closeAccount = new MyButton("Close Account");
        closeAccount.setAlignment(Pos.CENTER);
        closeAccount.setOnMousePressed((MouseEvent event) -> {
            bSound.play();
            bcr.closeClientAccount(nameTF.getText(), iban.getText());
            switchScene(getManageScene());
        });
        grid.add(closeAccount, 0, 6);
        GridPane.setHalignment(closeAccount, HPos.CENTER);
        // Button Return
        Button returnMenu = new MyButton("Return to the menu");
        returnMenu.setAlignment(Pos.CENTER);
        returnMenu.setOnMousePressed((MouseEvent event) -> {
            bSound.play();
            switchScene(getManageScene());
        });

        grid.add(returnMenu, 1, 6);
        GridPane.setHalignment(returnMenu, HPos.CENTER);
        return scene;
    }

    private Scene getListAllClientsScene() {
        getStage().setTitle("Bank Management System - All clients");
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 500, 420);
        grid.setMaxSize(500, 420);
        grid.setMinSize(500, 420);
        grid.setPrefSize(500, 420);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));

        // CONSTRUCT THIS PANE
        List<Client> clients = bcr.getClients();

        // Avatar
        ImageView iv1 = new ImageView(new Image(getClass().getResourceAsStream("Images/avatar-more.png"), 170, 170, true, false));
        grid.add(iv1, 0, 1);
        GridPane.setHalignment(iv1, HPos.CENTER);
        GridPane.setColumnSpan(iv1, 2);

        // EMPTY ROW
        final Pane spring = new Pane();
        spring.setPrefHeight(25);
        grid.add(spring, 0, 2);

        // Name
        // Label
        Label name = new Label("List of Clients: ");
        name.setStyle("-fx-font-weight: bold");
        grid.add(name, 0, 3);
        GridPane.setHalignment(name, HPos.CENTER);
        GridPane.setColumnSpan(name, 2);
        // Account List
        ListView<String> list = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        clients.forEach((cli) -> {
            items.add(cli.getName());
        });
        list.setItems(items);
        int limite = 3;
        if (items.size() <= limite) {
            list.setPrefHeight(items.size() * (list.getFixedCellSize() + 32));
        } else {
            list.setPrefHeight(limite * (list.getFixedCellSize() + 32));
        }
        //list.setDisable(true);
        grid.add(list, 0, 4);
        GridPane.setHalignment(list, HPos.CENTER);
        GridPane.setColumnSpan(list, 2);
        // EMPTY ROW
        final Pane spring1 = new Pane();
        spring1.setPrefHeight(25);
        grid.add(spring1, 0, 5);

        // Button Show Client
        Button showClient = new MyButton("Show Client");
        showClient.setOnMousePressed((MouseEvent event) -> {
            bSound.play();
            try {
                if (!list.getSelectionModel().getSelectedItem().isEmpty()) {
                    switchScene(getFindClientScene(list.getSelectionModel().getSelectedItem()));
                }
            } catch (Exception ex) {
                switchScene(getManageScene());
            }
        });
        grid.add(showClient, 0, 6);
        GridPane.setHalignment(showClient, HPos.CENTER);
        // Button Return
        Button returnMenu = new MyButton("Return to the menu");
        returnMenu.setAlignment(Pos.CENTER);
        returnMenu.setOnMousePressed((MouseEvent event) -> {
            bSound.play();
            switchScene(getManageScene());
        });

        grid.add(returnMenu, 1, 6);
        GridPane.setHalignment(returnMenu, HPos.CENTER);
        return scene;
    }

    private Scene getShowAccountScene(String accountIBAN, String clientName) {
        getStage().setTitle("Bank Management System - " + clientName + "'s account");
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 700, 450);
        grid.setMaxSize(700, 450);
        grid.setMinSize(700, 450);
        grid.setPrefSize(700, 450);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));

        // CONSTRUCT THIS PANE
        Account acc = bcr.getClient(clientName).getAccount(accountIBAN);

        // CONSTRUCT THIS PANE
        // Avatar
        ImageView iv1 = new ImageView(new Image(getClass().getResourceAsStream("Images/avatar.png"), 100, 100, true, false));
        grid.add(iv1, 0, 1);
        GridPane.setHalignment(iv1, HPos.CENTER);
        GridPane.setColumnSpan(iv1, 2);

        // EMPTY ROW
        final Pane spring = new Pane();
        spring.setPrefHeight(25);
        grid.add(spring, 0, 2);

        // Name
        // Label
        Label nameLabel = new Label("Client's name: ");
        nameLabel.setStyle("-fx-font-weight: bold");
        grid.add(nameLabel, 0, 3);
        GridPane.setHalignment(nameLabel, HPos.RIGHT);
        // Text Field
        TextField nameShow = new TextField(clientName);
        nameShow.setDisable(true);
        grid.add(nameShow, 1, 3);
        GridPane.setHalignment(nameShow, HPos.LEFT);

        // IBAN
        // Label
        Label ibanLabel = new Label("Account IBAN: ");
        ibanLabel.setStyle("-fx-font-weight: bold");
        grid.add(ibanLabel, 0, 4);
        GridPane.setHalignment(ibanLabel, HPos.RIGHT);
        // Text Field
        TextField ibanShow = new TextField(acc.getAccountNumber());
        ibanShow.setDisable(true);
        grid.add(ibanShow, 1, 4);
        GridPane.setHalignment(ibanShow, HPos.LEFT);

        // Ammout
        // Label
        Label amountLabel = new Label("Amount: ");
        amountLabel.setStyle("-fx-font-weight: bold");
        grid.add(amountLabel, 0, 5);
        GridPane.setHalignment(amountLabel, HPos.RIGHT);
        // Text Field
        String texto = "" + acc.getTotalAmount();
        if (acc instanceof AccountEUR) {
            texto += " EUR";
        } else {
            texto += " RON";
        }
        TextField amountShow = new TextField(texto);
        amountShow.setDisable(true);
        grid.add(amountShow, 1, 5);
        GridPane.setHalignment(amountShow, HPos.LEFT);

        // Activity
        // Label
        Label accL = new Label("Activity: ");
        accL.setStyle("-fx-font-weight: bold");
        grid.add(accL, 0, 6);
        GridPane.setHalignment(accL, HPos.RIGHT);
        // Account List
        ListView<String> list = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        acc.getActivity().forEach((act) -> {
            items.add(act);
        });
        list.setItems(items);
        int limite = 3;
        if (items.size() <= limite) {
            list.setPrefHeight(items.size() * (list.getFixedCellSize() + 32));
        } else {
            list.setPrefHeight(limite * (list.getFixedCellSize() + 32));
        }
        list.setPrefWidth(400);
        //list.setDisable(true);
        grid.add(list, 1, 6);
        GridPane.setHalignment(list, HPos.LEFT);

        // EMPTY ROW
        final Pane spring1 = new Pane();
        spring1.setPrefHeight(25);
        grid.add(spring1, 0, 7);

        // Button Return
        Button returnMenu = new MyButton("Return to the menu");
        returnMenu.setAlignment(Pos.CENTER);
        returnMenu.setOnMousePressed((MouseEvent event) -> {
            bSound.play();
            switchScene(getManageScene());
        });

        grid.add(returnMenu, 0, 8);
        GridPane.setHalignment(returnMenu, HPos.CENTER);
        GridPane.setColumnSpan(returnMenu, 2);
        return scene;
    }

    class MyButton extends Button {

        public MyButton(String text) {
            super(text);
            this.setPrefWidth(150);
            this.setAlignment(Pos.CENTER);
            this.setOnMousePressed((MouseEvent event) -> {
                bSound.play();
            });

            this.setOnMouseEntered((MouseEvent event) -> {
                getScene().setCursor(Cursor.HAND);
            });

            this.setOnMouseExited((MouseEvent event) -> {
                getScene().setCursor(Cursor.DEFAULT);
            });
        }

    }
}
