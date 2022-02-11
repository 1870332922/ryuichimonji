package mod.ryuichimonji.potion;

import mod.ryuichimonji.RyuIchimonji;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionUnseenAid extends Potion{
	
	ResourceLocation tex = new ResourceLocation("ryuichimonji" + ":textures/gui/potion_icon_unseenaid.png");
	
	public PotionUnseenAid(){
		// TODO Auto-generated constructor stub
		super(true, 0x3333FF);
		this.setRegistryName(RyuIchimonji.MODID + ":PotionUnseenAid");
		this.setPotionName("effect." + RyuIchimonji.MODID + ":PotionUnseenAid");
	}
	
	@Override
	public void renderInventoryEffect(int x,int y,PotionEffect e, Minecraft mc) {
		mc.getTextureManager().bindTexture(tex);
		mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, 0, 0, 18, 18);
	}
	
	@Override
	public void renderHUDEffect(int x,int y,PotionEffect e, Minecraft mc,float alpha) {
		mc.getTextureManager().bindTexture(tex);
		mc.ingameGUI.drawTexturedModalRect(x + 3, y + 3, 0, 0, 18, 18);
	}
	
}
