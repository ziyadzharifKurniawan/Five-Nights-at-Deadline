package fnaf.aim.groupA.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ReadyDialogTable {
    private int dialogX, dialogY, dialogW, dialogH;
    private int titleH;
    private int buttonW, buttonH;
    private int yesX, yesY, noX, noY;
    private BitmapFont font;

    public ReadyDialogTable(int screenW, int screenH) {
        dialogW = (int)(screenW * 0.28f);
        dialogH = (int)(screenH * 0.17f);
        dialogX = (screenW - dialogW) / 2;
        dialogY = (screenH - dialogH) / 2;
        titleH = 60;
        buttonW = 150;
        buttonH = 60;
        yesX = dialogX + dialogW/2 - buttonW - 20;
        noX  = dialogX + dialogW/2 + 20;
        yesY = dialogY + 30;
        noY  = dialogY + 30;
        font = new BitmapFont();
        font.getData().setScale(2f);
    }

    public void draw(ShapeRenderer sr, SpriteBatch batch) {
        Color DARK_RED = new Color(0.55f,0.2f,0.2f, 1f);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.BLACK);
        sr.rect(dialogX, dialogY, dialogW, dialogH);
        sr.setColor(DARK_RED);
        sr.rectLine(dialogX, dialogY, dialogX+dialogW, dialogY, 3);
        sr.rectLine(dialogX, dialogY+dialogH, dialogX+dialogW, dialogY+dialogH, 3);
        sr.rectLine(dialogX, dialogY, dialogX, dialogY+dialogH, 3);
        sr.rectLine(dialogX+dialogW, dialogY, dialogX+dialogW, dialogY+dialogH, 3);
        sr.setColor(DARK_RED);
        sr.rect(dialogX, dialogY+dialogH-titleH, dialogW, titleH);
        sr.setColor(Color.BLACK);
        sr.rect(yesX, yesY, buttonW, buttonH);
        sr.rect(noX, noY, buttonW, buttonH);
        sr.setColor(DARK_RED);
        sr.rectLine(yesX, yesY, yesX+buttonW, yesY, 2);
        sr.rectLine(yesX, yesY+buttonH, yesX+buttonW, yesY+buttonH, 2);
        sr.rectLine(yesX, yesY, yesX, yesY+buttonH, 2);
        sr.rectLine(yesX+buttonW, yesY, yesX+buttonW, yesY+buttonH, 2);
        sr.rectLine(noX, noY, noX+buttonW, noY, 2);
        sr.rectLine(noX, noY+buttonH, noX+buttonW, noY+buttonH, 2);
        sr.rectLine(noX, noY, noX, noY+buttonH, 2);
        sr.rectLine(noX+buttonW, noY, noX+buttonW, noY+buttonH, 2);
        sr.end();

        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, "Ready to Play?", dialogX+dialogW/2f-100, dialogY+dialogH-15);
        font.draw(batch, "Yes", yesX+buttonW/2f-25, yesY+buttonH/2f+15);
        font.draw(batch, "No", noX+buttonW/2f-20, noY+buttonH/2f+15);
        batch.end();
    }

    public boolean checkYesClick(int x, int y) {
        return x >= yesX && x <= yesX+buttonW && y >= yesY && y <= yesY+buttonH;
    }

    public boolean checkNoClick(int x, int y) {
        return x >= noX && x <= noX+buttonW && y >= noY && y <= noY+buttonH;
    }
}
