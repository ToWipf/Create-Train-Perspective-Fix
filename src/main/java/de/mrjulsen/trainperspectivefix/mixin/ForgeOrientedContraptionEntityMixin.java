package de.mrjulsen.trainperspectivefix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.content.contraptions.OrientedContraptionEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

@Mixin(OrientedContraptionEntity.class)
public abstract class ForgeOrientedContraptionEntityMixin {

    private float lastRot = -1;

    @SuppressWarnings("resource")
    @Inject(method = "applyRotation", remap = false, at = @At(value = "RETURN"))
    public void onApplyRotation(Vec3 localPos, float partialTicks, CallbackInfoReturnable<Vec3> cir) {        
        OrientedContraptionEntity entity = (OrientedContraptionEntity)(Object)this;
        float angleYaw = entity.getViewYRot(partialTicks);        
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.getVehicle() != null && Minecraft.getInstance().player.getVehicle().is(entity) && entity instanceof OrientedContraptionEntity) {
            float diff = (lastRot - angleYaw) % 360.0F;
            Minecraft.getInstance().player.setYRot((Minecraft.getInstance().player.getYRot() + 180.0F + diff) % 360.0F - 180.0F);
        }        
        lastRot = angleYaw;
    }
}