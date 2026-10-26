package io.github.octomiku01.happyghastspeed.mixin;

import io.github.octomiku01.happyghastspeed.HappyGhastSpeed;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HappyGhast.class)
public abstract class HappyGhastMixin {
	// The attribute is synced, so the client predicting the ridden ghast gets the same speed.
	@Inject(method = "tick", at = @At("TAIL"))
	private void happyGhastSpeed$updateSpeed(CallbackInfo ci) {
		HappyGhast ghast = (HappyGhast) (Object) this;
		if (!ghast.level().isClientSide()) {
			HappyGhastSpeed.update(ghast);
		}
	}
}
