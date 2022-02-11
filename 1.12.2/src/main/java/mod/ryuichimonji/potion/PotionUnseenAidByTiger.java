package mod.ryuichimonji.potion;

import mod.ryuichimonji.RyuIchimonji;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionUnseenAidByTiger extends Potion{
	
	ResourceLocation tex = new ResourceLocation("ryuichimonji" + ":textures/gui/potion_icon_unseenaid.png");
	
	public PotionUnseenAidByTiger(){
		// TODO Auto-generated constructor stub
		super(true, 0xE7E7E7);
		this.setRegistryName(RyuIchimonji.MODID + ":PotionUnseenAidByTiger");
		this.setPotionName("effect." + RyuIchimonji.MODID + ":PotionUnseenAidByTiger");
	}
	
	@Override
	public void renderInventoryEffect(int x,int y,PotionEffect e, Minecraft mc) {
		mc.getTextureManager().bindTexture(tex);
		mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, 3*18, 0, 18, 18);
	}
	
	@Override
	public void renderHUDEffect(int x,int y,PotionEffect e, Minecraft mc,float alpha) {
		mc.getTextureManager().bindTexture(tex);
		mc.ingameGUI.drawTexturedModalRect(x + 3, y + 3, 3*18, 0, 18, 18);
	}
	
}