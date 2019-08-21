package button;

import control.GameControl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ButtonClickEvent implements ActionListener {

    private GameControl gameControl;

    public ButtonClickEvent(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JButton bt = (JButton) ae.getSource();
        int currentPostion = gameControl.getListPostion().get(bt.getText());
        int emptyPostion = gameControl.getListPostion().get(" ");
        if (canMove(emptyPostion, currentPostion) && gameControl.isIsWin() == false) {
            swapButton(emptyPostion, currentPostion, bt.getText());
            gameControl.increaseMoveCount();
            gameControl.checkGamePlay();
            
        }
    }

    private void swapButton(int emptyPostion, int currentPostion, String buttonName) {
        // swap empty and buttonName
        gameControl.getListButton().get(emptyPostion).getButton().setText(buttonName);
        gameControl.getListButton().get(currentPostion).getButton().setText(" ");
        //
        gameControl.getListPostion().put(" ", currentPostion);
        gameControl.getListPostion().put(buttonName, emptyPostion);
    }

    private boolean canMove(int emptyPostion, int currentPostion) {
        // x,y empty
        int xEmpty = gameControl.getListButton().get(emptyPostion).getX();
        int yEmpty = gameControl.getListButton().get(emptyPostion).getY();
        // x,y current
        int xCurrent = gameControl.getListButton().get(currentPostion).getX();
        int yCurrent = gameControl.getListButton().get(currentPostion).getY();
        // check left
        if ((xCurrent == xEmpty) && (yCurrent == (yEmpty - 1))) {
            return true;
        }
        if ((xCurrent == xEmpty) && (yCurrent == (yEmpty + 1))) {
            return true;
        }
        if (((xCurrent + 1) == xEmpty) && (yCurrent == yEmpty)) {
            return true;
        }
        return ((xCurrent - 1) == xEmpty) && (yCurrent == yEmpty);
    }

}
