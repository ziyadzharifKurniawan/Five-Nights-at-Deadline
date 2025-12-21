package fnaf.aim.groupA.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
public class RedDialogFactory {

    private static Drawable coloredDrawable(Color fillColor, int borderThickness, Color borderColor) {
        int size = 64;
        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        pixmap.setColor(fillColor);
        pixmap.fill();

        if (borderThickness > 0) {
            pixmap.setColor(borderColor);
            for (int i = 0; i < borderThickness; i++) {
                pixmap.drawRectangle(i, i, size - 2 * i, size - 2 * i);
            }
        }

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public static Dialog create(String title, String message, Stage uiStage,
                                Runnable onYes, Runnable onNo) {

        BitmapFont font = new BitmapFont();
        font.getData().setScale(2f); // scale up default font

        Color DARK_RED = new Color(0.55f, 0f, 0f, 1f);

        Drawable dialogBg = coloredDrawable(Color.BLACK, 3, DARK_RED);
        Drawable titleBg = coloredDrawable(DARK_RED, 0, DARK_RED);
        Drawable buttonBg = coloredDrawable(Color.BLACK, 2, DARK_RED);

        Window.WindowStyle windowStyle = new Window.WindowStyle(font, Color.WHITE, dialogBg);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = buttonBg;
        buttonStyle.down = buttonBg;
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;

        Dialog dialog = new Dialog(title, windowStyle) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    if (onYes != null) onYes.run();
                } else {
                    if (onNo != null) onNo.run();
                }
            }
        };

        dialog.getTitleLabel().setFontScale(2.2f);
        dialog.getTitleTable().setBackground(titleBg);

        // Size to 45% width, 30% height
        float w = uiStage.getWidth() * 0.45f;
        float h = uiStage.getHeight() * 0.3f;
        dialog.setSize(w, h);
        dialog.setPosition((uiStage.getWidth() - w) / 2f, (uiStage.getHeight() - h) / 2f);

        // Message label
        LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
        Label contentLabel = new Label(message, labelStyle);
        contentLabel.setFontScale(1.5f);
        dialog.getContentTable().add(contentLabel)
            .padTop(h * 0.12f)
            .padBottom(h * 0.12f)
            .center()
            .row();
        dialog.getButtonTable().defaults().pad(20f).width(w * 0.35f).height(h * 0.25f);
        dialog.button(new TextButton("Yes", buttonStyle), true);
        dialog.button(new TextButton("No", buttonStyle), false);

        dialog.show(uiStage);
        return dialog;
    }
}
