package net.insane96mcp.xpholder.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.jcraft.jogg.Packet;

import net.insane96mcp.xpholder.XpHolder;
import net.insane96mcp.xpholder.network.DepositMessage;
import net.insane96mcp.xpholder.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import scala.inline;

public class TestGui extends GuiScreen{
	private final int GUI_WIDTH = 176;
	private final int GUI_HEIGHT = 82;
	private static ResourceLocation GUI_TEXTURE;
	private ButtonClose buttonClose;
	private GuiButton buttonWithdraw;
	private GuiButton buttonDeposit;
	private static ResourceLocation VANILLA_TEXTURES;
	
	private static int buttonId = 0;
	
	private int x, y, z;
	public int xpHeld = 0;
	
	public TestGui(int x, int y, int z) {
		GUI_TEXTURE = new ResourceLocation(XpHolder.MOD_ID, "textures/gui/xpholder.png");
		VANILLA_TEXTURES = new ResourceLocation("textures/gui/icons.png");
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void initGui() {
        Keyboard.enableRepeatEvents(true);
        
		buttonClose = new ButtonClose(buttonId++, (width / 2) + (GUI_WIDTH / 2) + 2, (height / 2) - (GUI_HEIGHT / 2) + 2, "x");
		buttonList.add(buttonClose);

		buttonWithdraw = new GuiButton(buttonId++, (width / 2) - (GUI_WIDTH / 2) + 4, (height / 2) + (GUI_HEIGHT / 2) - 24, 80, 20, "Withdraw");
		buttonList.add(buttonWithdraw);
		
		buttonDeposit = new GuiButton(buttonId++, (width / 2) + (GUI_WIDTH / 2) - 84, (height / 2) + (GUI_HEIGHT / 2) - 24, 80, 20, "Deposit");
		buttonList.add(buttonDeposit);
	}
	
	@Override
	public void updateScreen() {
		buttonWithdraw.enabled = xpHeld > 0;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		Minecraft minecraft = Minecraft.getMinecraft();
		minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
		int offsetScreenLeft = (width - GUI_WIDTH) / 2;
		int offsetScreenTop = (height - GUI_HEIGHT) / 2;
		drawTexturedModalRect(offsetScreenLeft, offsetScreenTop, 0, 0, GUI_WIDTH, GUI_HEIGHT);
		
		//Draw XP Bar
		int cap = minecraft.player.xpBarCap();
        int left = width / 2 - 91;
        
        if (cap > 0)
        {
            short barWidth = 162;
            int filled = (int)(this.xpHeld * (float)(barWidth + 1));
            int top = height - 32 + 3;
            drawTexturedModalRect((width / 2) - (GUI_WIDTH / 2) + 7, (height / 2) - (GUI_HEIGHT / 2) + 13, 0, 82, barWidth, 5);

            if (filled > 0)
            {
                drawTexturedModalRect((width / 2) - (GUI_WIDTH / 2) + 7, (height / 2) - (GUI_HEIGHT / 2) + 13, 0, 87, filled, 5);
            }
        }
        
        //Draw Level number
        boolean flag1 = false;
        int color = flag1 ? 16777215 : 8453920;
        String text = "" + mc.player.experienceLevel;
        int x = (width - fontRenderer.getStringWidth(text)) / 2;
        int y = (height / 2) - (GUI_HEIGHT / 2) + 7;
        fontRenderer.drawString(text, x + 1, y, 0);
        fontRenderer.drawString(text, x - 1, y, 0);
        fontRenderer.drawString(text, x, y + 1, 0);
        fontRenderer.drawString(text, x, y - 1, 0);
        fontRenderer.drawString(text, x, y, color);
        
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.equals(buttonClose))
			mc.displayGuiScreen((GuiScreen)null);
		else if (button.equals(buttonWithdraw)){
			
		}
		else if (button.equals(buttonDeposit)){
			PacketHandler.SendToServer(new DepositMessage(2, new BlockPos(x, y, z)));
		}
	}
	
    @Override
    public boolean doesGuiPauseGame() {
    	return false;
    }
    
    static class ButtonClose extends GuiButton{

		public ButtonClose(int buttonId, int x, int y, String buttonText) {
			super(buttonId, x, y, 11, 11, "");
		}
    	
		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if (visible) {
				boolean isButtonHover = (mouseX >= this.x 
					&& mouseY >= this.y 
					&& mouseX < this.x + width 
					&& mouseY < this.y + height);

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(GUI_TEXTURE);
				int textureX = 176;
				int textureY = 0;
				
				if (isButtonHover)
					textureY += 11;
				
				drawTexturedModalRect(this.x, this.y, textureX, textureY, 11, 11);
			}
		}
    }
}
