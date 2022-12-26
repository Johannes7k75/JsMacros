package xyz.wagyourtail.jsmacros.client.api.helpers;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import xyz.wagyourtail.jsmacros.core.helpers.BaseHelper;

/**
 * @author Wagyourtail
 * @since 1.2.4
 */
@SuppressWarnings("unused")
public class StatusEffectHelper extends BaseHelper<StatusEffectInstance> {
    
    public StatusEffectHelper(StatusEffectInstance s) {
        super(s);
    }
    
    /**
     * @since 1.2.4
     * @return
     */
    public String getId() {
        return Registries.STATUS_EFFECT.getId(base.getEffectType()).toString();
    }
    
    /**
     * @since 1.2.4
     * @return
     */
    public int getStrength() {
        return base.getAmplifier();
    }
    
    /**
     * @since 1.2.4
     * @return
     */
    public int getTime() {
        return base.getDuration();
    }

    /**
     * @return {@code true} if this effect is applied permanently, {@code false} otherwise.
     *
     * @since 1.8.4
     */
    public boolean isPermanent() {
        return false;
    }

    /**
     * Ambient effects are usually applied through beacons and they make the particles more
     * translucent.
     *
     * @return {@code true} if this effect is an ambient one, {@code false} otherwise.
     *
     * @since 1.8.4
     */
    public boolean isAmbient() {
        return base.isAmbient();
    }

    /**
     * @return {@code true} if this effect has an icon it should render, {@code false} otherwise.
     *
     * @since 1.8.4
     */
    public boolean hasIcon() {
        return base.shouldShowIcon();
    }

    /**
     * @return {@code true} if this effect affects the particle color and gets rendered in game,
     *         {@code false} otherwise.
     *
     * @since 1.8.4
     */
    public boolean isVisible() {
        return base.shouldShowParticles();
    }

    /**
     * An effect which is instant can still have a duration, but only if it's set through a
     * command.
     *
     * @return {@code true} if this effect should be applied instantly, {@code false} otherwise.
     *
     * @since 1.8.4
     */
    public boolean isInstant() {
        return base.getEffectType().isInstant();
    }

    /**
     * @return {@code true} if this effect is considered beneficial, {@code false} otherwise.
     *
     * @since 1.8.4
     */
    public boolean isBeneficial() {
        return base.getEffectType().getCategory() == StatusEffectCategory.BENEFICIAL;
    }

    /**
     * @return {@code true} if this effect is considered neutral, {@code false} otherwise.
     *
     * @since 1.8.4
     */
    public boolean isNeutral() {
        return base.getEffectType().getCategory() == StatusEffectCategory.NEUTRAL;
    }

    /**
     * @return {@code true} if this effect is considered harmful, {@code false} otherwise.
     *
     * @since 1.8.4
     */
    public boolean isHarmful() {
        return base.getEffectType().getCategory() == StatusEffectCategory.HARMFUL;
    }

    @Override
    public String toString() {
        return String.format("StatusEffectHelper:{\"id\": \"%s\", \"strength\": %d, \"time\": %d}", getId(), getStrength(), getTime());
    }
    
}