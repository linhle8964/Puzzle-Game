package control;

import button.Button;
import button.ButtonClickEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import ui.GameUI;

public class GameControl {

    private GameUI gameUI;
    private int gameSize;
    private int limit;
    private ArrayList<Button> listButton;
    private ArrayList<Integer> listNumber;
    private HashMap<String, Integer> listPostion;
    private int timeCount;
    private int moveCount;
    volatile boolean isWin = true;
    Thread threadCountTime;

    public GameControl(int gameSize) {
        this.gameSize = gameSize;
        this.limit = gameSize * gameSize;
        startThread(1000);
    }

    private void startThread(int sleepTime) {
        threadCountTime = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (!isWin) {
                        try {
                            gameUI.setTxtTime((timeCount++) + " sec");
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GameControl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        };
    }

    public boolean checkGameWin() {
        for (int i = 1; i < limit; i++) {
            if (!listButton.get(i - 1).getButton().getText().equals("" + i)) {
                return false;
            }
        }
        return true;
    }

    public void checkGamePlay() {
        if (checkGameWin()) {
            isWin = !isWin;
            int ans = gameUI.getUserAnswer("You win! Play new game?");
            if (ans == 0) {
                startNewGame();
            }
        }
    }

    public void increaseMoveCount() {
        gameUI.setTxtMove("" + (++moveCount));
    }

    public void startNewGame() {
        timeCount = 0;
        moveCount = 0;
        if (gameUI != null) {
            gameUI.dispose();
        }
        gameUI = new GameUI(this, gameSize);

        if (!threadCountTime.isAlive()) {
            threadCountTime.start();
        }

        generatorNumber();
        System.out.print(checkCanWin2(listNumber));

        addButtonToList();
        displayListButton();

        gameUI.setVisible(true);
    }

    private void displayListButton() {
        listPostion = new HashMap<>();
        for (int i = 0; i < limit - 1; i++) {
            JButton btn = listButton.get(i).getButton();
            btn.setText("" + listNumber.get(i));
            gameUI.addButton(btn);
            listPostion.put("" + listNumber.get(i), i);
        }
        // add last empty button
        JButton btn = listButton.get(limit - 1).getButton();
        listPostion.put(" ", (limit - 1));
        gameUI.addButton(btn);
    }

    private void addButtonToList() {
        listButton = new ArrayList<>();
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < (gameSize); j++) {
                JButton btn = new JButton(" ");
                listButton.add(new Button(i, j, btn));
                btn.addActionListener(new ButtonClickEvent(this));
            }
        }
    }

    private boolean checkCanWin(ArrayList<Integer> list) {

        int count = 0;
        try {
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = i + 1; j < list.size(); j++) {

                    if (list.get(j) < list.get(i)) {
                        count++;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        if (count % 2 == 0) {
            return true;
        }

        return false;
    }

    private int checkCanWin2(ArrayList<Integer> list) {

        int count = 0;
       try {
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = i + 1; j < list.size(); j++) {

                    if (list.get(j) < list.get(i)) {
                        count++;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return count;
    }

    private void generatorNumber() {
        listNumber = new ArrayList<>();
        while (listNumber.size() < (limit - 1)) {
            Integer number = new Random().nextInt(limit);
            if (number != 0 && !listNumber.contains(number)) {
                listNumber.add(number);
            }

            if (listNumber.size() == limit - 1) {
                if (!checkCanWin(listNumber)) {
                    listNumber.clear();
                }
            }

        }
    }

    public ArrayList<Button> getListButton() {
        return listButton;
    }

    public HashMap<String, Integer> getListPostion() {
        return listPostion;
    }

    public void setGameSize(int gameSize) {
        this.gameSize = gameSize;
    }

    public void setLimit(int limit) {
        this.limit = gameSize * gameSize;
    }

    public int getLimit() {
        return limit;
    }

    public boolean isIsWin() {
        return isWin;
    }

    public void setIsWin(boolean isWin) {
        this.isWin = isWin;
    }

}
