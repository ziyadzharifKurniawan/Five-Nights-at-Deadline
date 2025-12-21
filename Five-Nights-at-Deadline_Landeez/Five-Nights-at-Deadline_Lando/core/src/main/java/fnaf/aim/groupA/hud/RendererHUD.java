package fnaf.aim.groupA.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class RendererHUD {
    private BatteryMeterHUD battery;
    private SanityMeterHUD sanity;
    private LabReportMeterHUDHUD labReport;
    private Texture batteryIcon;
    private Texture sanityIcon;
    private Texture labReportIcon;

    public RendererHUD(BatteryMeterHUD battery, SanityMeterHUD sanity, LabReportMeterHUDHUD labReport,
                       Texture batteryIcon, Texture sanityIcon, Texture labReportIcon) {
        this.battery = battery;
        this.sanity = sanity;
        this.labReport = labReport;
        this.batteryIcon = batteryIcon;
        this.sanityIcon = sanityIcon;
        this.labReportIcon = labReportIcon;
    }

    public void renderBars(ShapeRenderer shape) {
        battery.render(shape);
        sanity.render(shape);
        labReport.render(shape);
    }

    public void renderIcons(SpriteBatch batch) {
        float iconSize = battery.getHeight() * 1.5f;
        batch.draw(batteryIcon, battery.getX() - iconSize, battery.getY(), iconSize, iconSize);
        batch.draw(sanityIcon, sanity.getX() - iconSize, sanity.getY(), iconSize, iconSize);
        batch.draw(labReportIcon, labReport.getX() - iconSize, labReport.getY(), iconSize, iconSize);
    }
}
