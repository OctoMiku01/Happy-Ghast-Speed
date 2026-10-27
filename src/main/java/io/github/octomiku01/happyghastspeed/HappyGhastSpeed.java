package io.github.octomiku01.happyghastspeed;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.player.Player;

public final class HappyGhastSpeed {
	public static final String MOD_ID = "happy_ghast_speed";

	// Vanilla is 0.05. Ridden flight speed grows with the square of this value, so 0.08 flies about two and a half times as fast.
	private static final double RIDDEN_SPEED = 0.08;
	private static final double IDLE_SPEED = 0.03;

	private static final Identifier MODIFIER_ID = Identifier.fromNamespaceAndPath(MOD_ID, "flying_speed");

	private HappyGhastSpeed() {
	}

	public static void update(HappyGhast ghast) {
		AttributeInstance speed = ghast.getAttribute(Attributes.FLYING_SPEED);
		if (speed == null) {
			return;
		}
		double target = ghast.hasPassenger(passenger -> passenger instanceof Player) ? RIDDEN_SPEED : IDLE_SPEED;
		// Relative to the base value, so ghasts whose base was overwritten (e.g. by a data pack) still end up at the target.
		double amount = target - speed.getBaseValue();
		AttributeModifier current = speed.getModifier(MODIFIER_ID);
		if (current == null || current.amount() != amount) {
			// Transient: never saved, so removing the mod leaves the ghasts at vanilla speed.
			speed.addOrUpdateTransientModifier(new AttributeModifier(MODIFIER_ID, amount, AttributeModifier.Operation.ADD_VALUE));
		}
	}
}
