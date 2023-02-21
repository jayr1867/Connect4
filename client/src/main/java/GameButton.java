import javafx.scene.control.Button;

public class GameButton extends Button {

    Button gameB;
    Integer row;
    Integer col;
    Boolean used;
    GameButton(Integer i, Integer j) {
        gameB = new Button();
        row = j;
        col = i;
        used = false;
    }


}
