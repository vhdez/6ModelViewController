package org.sla;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Controller {
    public Button nextButton;
    public TextArea textArea;
    public ImageView imageView;

    public void initialize() {
        File greenieFile = new File("res/Greenie.jpg");
        imageView.setImage(new Image(greenieFile.toURI().toString()));

        textArea.setWrapText(true);
    }

    public void nextAlbum() {
        String nextAlbumDescription = AlbumModel.describeNextAlbum();
        textArea.appendText(nextAlbumDescription);
    }
}
