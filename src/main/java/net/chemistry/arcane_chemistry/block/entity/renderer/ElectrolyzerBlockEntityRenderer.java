package net.chemistry.arcane_chemistry.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.chemistry.arcane_chemistry.block.custom.ElectrolyzerBlock;
import net.chemistry.arcane_chemistry.block.entity.custom.CentrifugeBlockEntity;
import net.chemistry.arcane_chemistry.block.entity.custom.ElectrolyzerBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;

public class ElectrolyzerBlockEntityRenderer implements BlockEntityRenderer<ElectrolyzerBlockEntity> {

    private final ItemRenderer itemRenderer;
    private float rotation;

    public ElectrolyzerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ElectrolyzerBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Lazy<IItemHandler> itemHandler = blockEntity.getItemHandler();
        Level level = blockEntity.getLevel();
        BlockPos pos = blockEntity.getBlockPos();

        poseStack.pushPose();
        poseStack.translate(0.5, 0.7, 0.5);
        poseStack.scale(0.3f, 0.3f, 0.3f);

        rotation += 1.0f * partialTick;

        float radius = 0.5f;
        int itemCount = Math.min(itemHandler.get().getSlots(), 5);

        for (int i = 0; i < itemCount; i++) {
            ItemStack stack = itemHandler.get().getStackInSlot(i);

            if (stack.is(Items.WATER_BUCKET)) {
                continue;
            }

            poseStack.pushPose();

            float angle = (rotation + (360f / itemCount) * i) * (float) Math.PI / 180f;

            float x = radius * (float) Math.cos(angle);
            float z = radius * (float) Math.sin(angle);

            poseStack.translate(x, 0.15, z);

            float itemRotation = rotation * 1.5f;
            poseStack.mulPose(Axis.YP.rotationDegrees(itemRotation));

            poseStack.mulPose(Axis.YP.rotationDegrees(90));

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(level, pos), packedOverlay, poseStack, bufferSource, level, 1);

            poseStack.popPose();
        }

        poseStack.popPose();
    }


    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
