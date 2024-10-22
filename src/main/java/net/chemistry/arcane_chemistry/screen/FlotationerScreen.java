package net.chemistry.arcane_chemistry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.chemistry.arcane_chemistry.Arcane_chemistry;
import net.chemistry.arcane_chemistry.screen.renderer.FluidTankRenderer;
import net.chemistry.arcane_chemistry.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import java.util.Optional;

public class FlotationerScreen extends AbstractContainerScreen<FlotationerMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Arcane_chemistry.MOD_ID, "textures/gui/flotationer_gui.png");

    public FlotationerScreen(FlotationerMenu container, Inventory inventory, Component title) {
        super(container, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 74;
        this.titleLabelY = 12;
        this.titleLabelX = 43;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        super.renderLabels(guiGraphics, pMouseX, pMouseY);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        int progressArrowWidth = menu.getScaledProgress();
        guiGraphics.blit(TEXTURE, x + 79, y + 49, 176, 0, progressArrowWidth, 15);


        FluidTank tank = this.menu.blockentity.getFluidTank();
        FluidStack fluidStack = tank.getFluid();
        if(fluidStack.isEmpty())
            return;

        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(fluidStack);
        if(stillTexture == null)
            return;

        TextureAtlasSprite sprite =
                this.minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
        int tintColor = fluidTypeExtensions.getTintColor(fluidStack);

        float alpha = ((tintColor >> 24) & 0xFF) / 255f;
        float red = ((tintColor >> 16) & 0xFF) / 255f;
        float green = ((tintColor >> 8) & 0xFF) / 255f;
        float blue = (tintColor & 0xFF) / 255f;

        guiGraphics.setColor(red, green, blue, alpha);

        int fluidHeight = getFluidHeight(tank);
        guiGraphics.blit(
                this.leftPos + 100,
                getFluidY(fluidHeight),
                0,
                16,
                fluidHeight,
                sprite);

        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidTankRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBg(guiGraphics, partialTick, mouseX, mouseY);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);

        FluidTank tank = this.menu.blockentity.getFluidTank();
        FluidStack fluidStack = tank.getFluid();
        if(fluidStack.isEmpty())
            return;

        int fluidHeight = getFluidHeight(tank);
        if(!isHovering(100, getFluidY(fluidHeight) - this.topPos, 16, fluidHeight, mouseX, mouseY))
            return;

        Component component = MutableComponent.create(fluidStack.getHoverName().getContents())
                .append(" %s / %s mB".formatted(tank.getFluidAmount(), tank.getCapacity()));
        guiGraphics.renderTooltip(this.font, component, mouseX, mouseY);
    }

    private static int getFluidHeight(IFluidTank tank) {
        return (int) (48 * ((float) tank.getFluidAmount() / tank.getCapacity()));
    }

    private int getFluidY(int fluidHeight) {
        return this.topPos + 15 + (48 - fluidHeight);
    }
}
