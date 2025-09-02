package jonius7.ftafix;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class PatchedFtaGui extends GuiScreen {

    private final GuiScreen parentGuiScreen;

    public PatchedFtaGui(GuiScreen parentGuiScreen) {
        this.parentGuiScreen = parentGuiScreen;
    }

    @Override
    public void initGui() {
        // Add a "Continue" button centered horizontally
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + 40, "Continue"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Draw a dark background to mimic vanilla error screens
        this.drawDefaultBackground();

        // Main messages
        this.drawCenteredString(
            this.fontRendererObj,
            "FullThrottle Alchemist was patched to fix a Forestry API conflict.",
            this.width / 2,
            this.height / 2 - 10,
            0xFFFFFF
        );

        this.drawCenteredString(
            this.fontRendererObj,
            "Without this, a mod cycle error would occur.",
            this.width / 2,
            this.height / 2 + 10,
            0xAAAAAA
        );

        // Draw buttons
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) { // Continue button
            this.mc.displayGuiScreen(parentGuiScreen);
        }
    }
}
