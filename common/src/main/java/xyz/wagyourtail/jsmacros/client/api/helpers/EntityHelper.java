package xyz.wagyourtail.jsmacros.client.api.helpers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.FurnaceMinecartEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

import xyz.wagyourtail.jsmacros.client.access.IMixinEntity;
import xyz.wagyourtail.jsmacros.client.api.sharedclasses.PositionCommon;
import xyz.wagyourtail.jsmacros.client.api.helpers.entity.specialized.boss.EnderDragonEntityHelper;
import xyz.wagyourtail.jsmacros.client.api.helpers.entity.specialized.boss.WitherEntityHelper;
import xyz.wagyourtail.jsmacros.client.api.helpers.entity.specialized.decoration.*;
import xyz.wagyourtail.jsmacros.client.api.helpers.entity.specialized.mob.*;
import xyz.wagyourtail.jsmacros.client.api.helpers.entity.specialized.passive.*;
import xyz.wagyourtail.jsmacros.client.api.helpers.entity.specialized.projectile.*;
import xyz.wagyourtail.jsmacros.client.api.helpers.entity.specialized.vehicle.*;
import xyz.wagyourtail.jsmacros.core.helpers.BaseHelper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Wagyourtail
 *
 */
@SuppressWarnings("unused")
public class EntityHelper<T extends Entity> extends BaseHelper<T> {
    
    protected EntityHelper(T e) {
        super(e);
    }
    
    /**
     * @return entity position.
     */
    public PositionCommon.Pos3D getPos() {
        return new PositionCommon.Pos3D(base.getX(), base.getY(), base.getZ());
    }

    /**
     * @return entity block position.
     *
     * @since 1.6.5
     */
    public BlockPosHelper getBlockPos() {
        return new BlockPosHelper(base.getBlockPos());
    }

    /**
     * @return the entity's eye position.
     *
     * @since 1.8.4
     */
    public PositionCommon.Pos3D getEyePos() {
        return new PositionCommon.Pos3D(base.getEyePos());
    }
    
    /**
     * @return entity chunk coordinates. Since Pos2D only has x and y fields, z coord is y.
     *
     * @since 1.6.5
     */
    public PositionCommon.Pos2D getChunkPos() {
        return new PositionCommon.Pos2D(base.getChunkPos().x, base.getChunkPos().z);
    }
    
    /**
     * @since 1.0.8
     * @return the {@code x} value of the entity.
     */
    public double getX() {
        return base.getX();
    }

    /**
     * @since 1.0.8
     * @return the {@code y} value of the entity.
     */
    public double getY() {
        return base.getY();
    }
    
    /**
     * @since 1.0.8
     * @return the {@code z} value of the entity.
     */
    public double getZ() {
        return base.getZ();
    }

    /**
     * @since 1.2.8
     * @return the current eye height offset for the entitye.
     */
    public double getEyeHeight() {
        return base.getEyeHeight(base.getPose());
    }

    /**
     * @since 1.0.8
     * @return the {@code pitch} value of the entity.
     */
    public float getPitch() {
        return base.getPitch();
    }
    
    /**
     * @since 1.0.8
     * @return the {@code yaw} value of the entity.
     */
    public float getYaw() {
        return MathHelper.wrapDegrees(base.getYaw());
    }
    
    /**
     * @return the name of the entity.
     * @since 1.0.8 [citation needed], returned string until 1.6.4
     */
    public TextHelper getName() {
        return new TextHelper(base.getName());
    }
    
    /**
     * @return the type of the entity.
     */
    public String getType() {
        return EntityType.getId(base.getType()).toString();
    }
    
    /**
     * @since 1.1.9
     * @return if the entity has the glowing effect.
     */
    public boolean isGlowing() {
        return base.isGlowing();
    }
    
    /**
     * @since 1.1.9
     * @return if the entity is in lava.
     */
    public boolean isInLava() {
        return base.isInLava();
    }
    
    /**
     * @since 1.1.9
     * @return if the entity is on fire.
     */
    public boolean isOnFire() {
        return base.isOnFire();
    }

    /**
     * @return {@code true} if the entity is sneaking, {@code false} otherwise.
     *
     * @since 1.8.4
     */
    public boolean isSneaking() {
        return base.isSneaking();
    }

    /**
     * @return {@code true} if the entity is sprinting, {@code false} otherwise.
     *
     * @since 1.8.4
     */
    public boolean isSprinting() {
        return base.isSprinting();
    }
    
    /**
     * @since 1.1.8 [citation needed]
     * @return the vehicle of the entity.
     */
    public EntityHelper<?> getVehicle() {
        Entity parent = base.getVehicle();
        if (parent != null) return EntityHelper.create(parent);
        return null;
    }
    
    /**
     * @since 1.1.8 [citation needed]
     * @return the entity passengers.
     */
    public List<EntityHelper<?>> getPassengers() {
        List<EntityHelper<?>> entities = base.getPassengerList().stream().map(EntityHelper::create).collect(Collectors.toList());
        return entities.size() == 0 ? null : entities;
        
    }
    
    /**
     * @since 1.2.8, was a {@link String} until 1.5.0
     * @return
     */
    public NBTElementHelper<?> getNBT() {
        NbtCompound nbt = new NbtCompound();
        base.writeNbt(nbt);
        return NBTElementHelper.resolve(nbt);
    }

    /**
     * @since 1.6.4
     * @param name
     */
    public EntityHelper<T> setCustomName(TextHelper name) {
        if (name == null) {
            base.setCustomName(null);
        } else {
            base.setCustomName(name.getRaw());
        }
        return this;
    }

    /**
     * sets the name to always display
     * @since 1.8.0
     * @param b
     */
    public EntityHelper<T> setCustomNameVisible(boolean b) {
        base.setCustomNameVisible(b);
        return this;
    }

    /**
     * @param color
     */
    public EntityHelper<T> setGlowingColor(int color) {
        ((IMixinEntity) base).jsmacros_setGlowingColor(color);
        return this;
    }

    /**
     *
     */
    public EntityHelper<T> resetGlowingColor() {
        ((IMixinEntity) base).jsmacros_resetColor();
        return this;
    }

    /**
     * warning: affected by setGlowingColor
     * @since 1.8.2
     * @return glow color
     */
    public int getGlowingColor() {
        return base.getTeamColorValue();
    }

    /**
     * Sets whether the entity is glowing.
     * @since 1.1.9
     * @param val
     * @return
     */
    public EntityHelper<T> setGlowing(boolean val) {
        ((IMixinEntity) base).jsmacros_setForceGlowing(val ? 2 : 0);
        return this;
    }

    /**
     * reset the glowing effect to proper value.
     * @since 1.6.3
     * @return
     */
    public EntityHelper<T> resetGlowing() {
        ((IMixinEntity) base).jsmacros_setForceGlowing(1);
        return this;
    }
    
    /**
     * Checks if the entity is still alive.
     * @since 1.2.8
     * @return
     */
    public boolean isAlive() {
        return base.isAlive();
    }

    /**
    * @since 1.6.5
    * @return UUID of the entity, random* if not a player, otherwise the player's uuid.
    */
    public String getUUID() {
        return base.getUuid().toString();
    }

    /**
     * @return the maximum amount of air this entity can have.
     *
     * @since 1.8.4
     */
    public int getMaxAir() {
        return base.getMaxAir();
    }

    /**
     * @return the amount of air this entity has.
     *
     * @since 1.8.4
     */
    public int getAir() {
        return base.getAir();
    }
    
    /**
     * @return this entity's current speed in blocks per second.
     *
     * @since 1.8.4
     */
    public double getSpeed() {
        double dx = Math.abs(base.getX() - base.prevX);
        double dz = Math.abs(base.getZ() - base.prevZ);
        return Math.sqrt(dx * dx + dz * dz) * 20;
    }

    /**
     * @return the direction the entity is facing, rounded to the nearest 45 degrees.
     *
     * @since 1.8.4
     */
    public DirectionHelper getFacingDirection() {
        return new DirectionHelper(base.getHorizontalFacing());
    }

    /**
     * @return the distance between this entity and the specified one.
     *
     * @since 1.8.4
     */
    public float distanceTo(EntityHelper<?> entity) {
        return base.distanceTo(entity.getRaw());
    }

    /**
     * @return the distance between this entity and the specified position.
     *
     * @since 1.8.4
     */
    public double distanceTo(BlockPosHelper pos) {
        return Math.sqrt(pos.getRaw().getSquaredDistance(base.getPos()));
    }

    /**
     * @return the distance between this entity and the specified position.
     *
     * @since 1.8.4
     */
    public double distanceTo(PositionCommon.Pos3D pos) {
        return Math.sqrt(base.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()));
    }

    /**
     * @return the distance between this entity and the specified position.
     *
     * @since 1.8.4
     */
    public double distanceTo(double x, double y, double z) {
        return Math.sqrt(base.squaredDistanceTo(x, y, z));
    }

    /**
     * @return the velocity vector.
     *
     * @since 1.8.4
     */
    public PositionCommon.Pos3D getVelocity() {
        return new PositionCommon.Pos3D(base.getVelocity().x, base.getVelocity().y, base.getVelocity().z);
    }

    /**
     * @return the chunk helper for the chunk this entity is in.
     *
     * @since 1.8.4
     */
    public ChunkHelper getChunk() {
        return new ChunkHelper(base.getWorld().getChunk(base.getBlockPos()));
    }
    
    /**
     * @return the name of the biome this entity is in.
     *
     * @since 1.8.4
     */
    public String getBiome() {
        return MinecraftClient.getInstance().world.getRegistryManager().get(Registry.BIOME_KEY).getId(MinecraftClient.getInstance().world.getBiome(base.getBlockPos()).value()).toString();
    }

    @Override
    public String toString() {
        return String.format("%s:{\"name\": \"%s\", \"type\": \"%s\"}", getClass().getSimpleName(), this.getName(), this.getType());
    }

    /**
     * mostly for internal use.
     *
     * @param e mc entity.
     *
     * @return correct subclass of this.
     */
    public static EntityHelper<?> create(Entity e) {
        // Players
        if (e instanceof ClientPlayerEntity) return new ClientPlayerEntityHelper<>((ClientPlayerEntity) e);
        if (e instanceof PlayerEntity) return new PlayerEntityHelper<>((PlayerEntity) e);
        
        if (e instanceof MobEntity) {
            // Merchants
            if (e instanceof VillagerEntity) return new VillagerEntityHelper((VillagerEntity) e);
            if (e instanceof MerchantEntity) return new MerchantEntityHelper<>((MerchantEntity) e);
            
            // Bosses
            if (e instanceof EnderDragonEntity) {
                return new EnderDragonEntityHelper(((EnderDragonEntity) e));
            } else if (e instanceof WitherEntity) {
                return new WitherEntityHelper(((WitherEntity) e));
            }

            // Hostile mobs
            if (e instanceof AbstractPiglinEntity) {
                if (e instanceof PiglinEntity) {
                    return new PiglinEntityHelper(((PiglinEntity) e));
                } else {
                    return new AbstractPiglinEntityHelper<>(((AbstractPiglinEntity) e));
                }
            } else if (e instanceof CreeperEntity) {
                return new CreeperEntityHelper(((CreeperEntity) e));
            } else if (e instanceof ZombieEntity) {
                if (e instanceof DrownedEntity) {
                    return new DrownedEntityHelper(((DrownedEntity) e));
                } else if (e instanceof ZombieVillagerEntity) {
                    return new ZombieVillagerEntityHelper(((ZombieVillagerEntity) e));
                } else {
                    return new ZombieEntityHelper<>(((ZombieEntity) e));
                }
            } else if (e instanceof EndermanEntity) {
                return new EndermanEntityHelper(((EndermanEntity) e));
            } else if (e instanceof GhastEntity) {
                return new GhastEntityHelper(((GhastEntity) e));
            } else if (e instanceof BlazeEntity) {
                return new BlazeEntityHelper(((BlazeEntity) e));
            } else if (e instanceof GuardianEntity) {
                return new GuardianEntityHelper(((GuardianEntity) e));
            } else if (e instanceof PhantomEntity) {
                return new PhantomEntityHelper(((PhantomEntity) e));
            } else if (e instanceof IllagerEntity) {
                if (e instanceof VindicatorEntity) {
                    return new VindicatorEntityHelper(((VindicatorEntity) e));
                } else if (e instanceof PillagerEntity) {
                    return new PillagerEntityHelper(((PillagerEntity) e));
                } else if (e instanceof SpellcastingIllagerEntity) {
                    return new SpellcastingIllagerEntityHelper<>(((SpellcastingIllagerEntity) e));
                } else {
                    return new IllagerEntityHelper<>(((IllagerEntity) e));
                }
            } else if (e instanceof ShulkerEntity) {
                return new ShulkerEntityHelper(((ShulkerEntity) e));
            } else if (e instanceof SlimeEntity) {
                return new SlimeEntityHelper(((SlimeEntity) e));
            } else if (e instanceof SpiderEntity) {
                return new SpiderEntityHelper(((SpiderEntity) e));
            } else if (e instanceof VexEntity) {
                return new VexEntityHelper(((VexEntity) e));
            } else if (e instanceof WardenEntity) {
                return new WardenEntityHelper(((WardenEntity) e));
            } else if (e instanceof WitchEntity) {
                return new WitchEntityHelper(((WitchEntity) e));
            }

            // Animals
            if (e instanceof AnimalEntity) {
                if (e instanceof AbstractHorseEntity) {
                    if (e instanceof HorseEntity) {
                        return new HorseEntityHelper(((HorseEntity) e));
                    } else if (e instanceof AbstractDonkeyEntity) {
                        if (e instanceof LlamaEntity) {
                            return new LlamaEntityHelper<>(((LlamaEntity) e));
                        } else {
                            return new DonkeyEntityHelper<>(((AbstractDonkeyEntity) e));
                        }
                    } else {
                        return new AbstractHorseEntityHelper<>(((AbstractHorseEntity) e));
                    }
                } else if (e instanceof AxolotlEntity) {
                    return new AxolotlEntityHelper(((AxolotlEntity) e));
                } else if (e instanceof BeeEntity) {
                    return new BeeEntityHelper(((BeeEntity) e));
                } else if (e instanceof FoxEntity) {
                    return new FoxEntityHelper(((FoxEntity) e));
                } else if (e instanceof FrogEntity) {
                    return new FrogEntityHelper(((FrogEntity) e));
                } else if (e instanceof GoatEntity) {
                    return new GoatEntityHelper(((GoatEntity) e));
                } else if (e instanceof MooshroomEntity) {
                    return new MooshroomEntityHelper(((MooshroomEntity) e));
                } else if (e instanceof OcelotEntity) {
                    return new OcelotEntityHelper(((OcelotEntity) e));
                } else if (e instanceof PandaEntity) {
                    return new PandaEntityHelper(((PandaEntity) e));
                } else if (e instanceof PigEntity) {
                    return new PigEntityHelper(((PigEntity) e));
                } else if (e instanceof PolarBearEntity) {
                    return new PolarBearEntityHelper(((PolarBearEntity) e));
                } else if (e instanceof RabbitEntity) {
                    return new RabbitEntityHelper(((RabbitEntity) e));
                } else if (e instanceof SheepEntity) {
                    return new SheepEntityHelper(((SheepEntity) e));
                } else if (e instanceof StriderEntity) {
                    return new StriderEntityHelper(((StriderEntity) e));
                } else if (e instanceof TameableEntity) {
                    if (e instanceof CatEntity) {
                        return new CatEntityHelper(((CatEntity) e));
                    } else if (e instanceof WolfEntity) {
                        return new WolfEntityHelper(((WolfEntity) e));
                    } else if (e instanceof ParrotEntity) {
                        return new ParrotEntityHelper(((ParrotEntity) e));
                    } else {
                        return new TameableEntityHelper<>(((TameableEntity) e));
                    }
                } else {
                    return new AnimalEntityHelper<>(((AnimalEntity) e));
                }
            }

            // Neutral mobs
            if (e instanceof AllayEntity) {
                return new AllayEntityHelper(((AllayEntity) e));
            } else if (e instanceof BatEntity) {
                return new BatEntityHelper(((BatEntity) e));
            } else if (e instanceof DolphinEntity) {
                return new DolphinEntityHelper(((DolphinEntity) e));
            } else if (e instanceof IronGolemEntity) {
                return new IronGolemEntityHelper(((IronGolemEntity) e));
            } else if (e instanceof SnowGolemEntity) {
                return new SnowGolemEntityHelper(((SnowGolemEntity) e));
            } else if (e instanceof FishEntity) {
                if (e instanceof PufferfishEntity) {
                    return new PufferfishEntityHelper(((PufferfishEntity) e));
                } else if (e instanceof TropicalFishEntity) {
                    return new TropicalFishEntityHelper(((TropicalFishEntity) e));
                } else {
                    return new FishEntityHelper<>(((FishEntity) e));
                }
            }
        }

        // Projectiles
        if (e instanceof ProjectileEntity) {
            if (e instanceof ArrowEntity) {
                return new ArrowEntityHelper(((ArrowEntity) e));
            } else if (e instanceof FishingBobberEntity) {
                return new FishingBobberEntityHelper(((FishingBobberEntity) e));
            } else if (e instanceof TridentEntity) {
                return new TridentEntityHelper(((TridentEntity) e));
            } else if (e instanceof WitherSkullEntity) {
                return new WitherSkullEntityHelper(((WitherSkullEntity) e));
            }
        }

        // Decorations
        if (e instanceof ArmorStandEntity) {
            return new ArmorStandEntityHelper(((ArmorStandEntity) e));
        } else if (e instanceof EndCrystalEntity) {
            return new EndCrystalEntityHelper(((EndCrystalEntity) e));
        } else if (e instanceof ItemFrameEntity) {
            return new ItemFrameEntityHelper(((ItemFrameEntity) e));
        } else if (e instanceof PaintingEntity) {
            return new PaintingEntityHelper(((PaintingEntity) e));
        }

        // Vehicles
        if (e instanceof BoatEntity) {
            return new BoatEntityHelper(((BoatEntity) e));
        } else if (e instanceof FurnaceMinecartEntity) {
            return new FurnaceMinecartEntityHelper(((FurnaceMinecartEntity) e));
        } else if (e instanceof TntMinecartEntity) {
            return new TntMinecartEntityHelper(((TntMinecartEntity) e));
        }
        
        if (e instanceof LivingEntity) return new LivingEntityHelper<>((LivingEntity) e);
        if (e instanceof ItemEntity) return new ItemEntityHelper((ItemEntity) e);
        return new EntityHelper<>(e);
    }

    /**
     * @since 1.6.3
     * @return cast of this entity helper (mainly for typescript)
     */
    public ClientPlayerEntityHelper<?> asClientPlayer() {
        return (ClientPlayerEntityHelper<?>) this;
    }

    /**
     * @since 1.6.3
     * @return cast of this entity helper (mainly for typescript)
     */
    public PlayerEntityHelper<?> asPlayer() {
        return (PlayerEntityHelper<?>) this;
    }

    /**
     * @since 1.6.3
     * @return cast of this entity helper (mainly for typescript)
     */
     public VillagerEntityHelper asVillager() {
         return (VillagerEntityHelper) this;
     }

    /**
     * @since 1.6.3
     * @return cast of this entity helper (mainly for typescript)
     */
    public MerchantEntityHelper<?> asMerchant() {
        return (MerchantEntityHelper<?>) this;
    }


    /**
     * @since 1.6.3
     * @return cast of this entity helper (mainly for typescript)
     */
    public LivingEntityHelper<?> asLiving() {
        return (LivingEntityHelper<?>) this;
    }

    /**
     * @return this helper as an animal entity helper (mainly for typescript).
     *
     * @since 1.8.4
     */
    public LivingEntityHelper<?> asAnimal() {
        return (AnimalEntityHelper<?>) this;
    }
    
    /**
     * @since 1.6.3
     * @return cast of this entity helper (mainly for typescript)
     */
    public ItemEntityHelper asItem() {
        return (ItemEntityHelper) this;
    }

    /**
     * @return the entity as a server entity if an integrated server is running and {@code null} otherwise.
     *
     * @since 1.8.4
     */
    public EntityHelper<?> asServerEntity() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!client.isIntegratedServerRunning()) {
            return null;
        }
        Entity entity = client.getServer().getPlayerManager().getPlayer(client.player.getUuid()).getWorld().getEntity(base.getUuid());
        if (entity == null) {
            return null;
        } else {
            return create(entity);
        }
    }
    
}