package net.jakymc.jakthing.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;


public class LivingPotato extends Animal {

    private static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(LivingPotato.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> FERTILIZE = SynchedEntityData.defineId(LivingPotato.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_SIZE = SynchedEntityData.defineId(LivingPotato.class, EntityDataSerializers.INT);
//    private static final EntityDataAccessor<Boolean> IS_MANA = SynchedEntityData.defineId(LivingPotato.class, EntityDataSerializers.BOOLEAN);

    protected LivingPotato(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this,1.35D));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.1D, Ingredient.of(Items.POTATO), true));
//        this.goalSelector.addGoal(6, new TemptGoal(this, 1.2D, Ingredient.of(Items.BONE_MEAL), false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH,5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18D);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return JakEntity.LivingPotato.get().create(pLevel);
    }
    public int getPotatoSize() {
        return this.entityData.get(SIZE);
    }
    public void setPotatoSize(int size) {
        this.entityData.set(SIZE, Mth.clamp(size, 0, getMaxSize()));
    }
    private int getFertilize() {
        return this.entityData.get(FERTILIZE);
    }
    private void setFertilize(int fertilize) {
        this.entityData.set(FERTILIZE, fertilize);
    }
    private int getMaxSize() {
        return this.entityData.get(MAX_SIZE);
    }
    private void setMaxSize(int maxsize) {
        this.entityData.set(MAX_SIZE, maxsize);
    }
//    public void setIsMana(boolean ismana){
//        this.entityData.set(IS_MANA,ismana);
//    }
//    public boolean isMana(){
//        return this.entityData.get(IS_MANA);
//    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SIZE, 0);
        this.entityData.define(FERTILIZE, 0);
        this.entityData.define(MAX_SIZE, 100);
//        this.entityData.define(IS_MANA,false);
    }
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setPotatoSize(compound.getInt("Size"));
        this.setFertilize(compound.getInt("Fertilize"));
        this.setMaxSize(compound.getInt("MaxSize"));
//        this.setIsMana(compound.getBoolean("isMana"));
    }
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Size", getPotatoSize());
        compound.putInt("Fertilize", getFertilize());
        compound.putInt("MaxSize", getMaxSize());
//        compound.putBoolean("isMana",isMana());
    }
    public EntityDimensions getDimensions(Pose pose) {
        int i = getPotatoSize();
        float f = 1.0F + 0.1F * (float)i;
        return super.getDimensions(pose).scale(f);
    }
    public void onSyncedDataUpdated(EntityDataAccessor<?> id) {
        if (SIZE.equals(id)) {
            this.updatePotatoSizeInfo();
        }
        if (FERTILIZE.equals(id)) {
            int Fertilize = getFertilize();
            if (Fertilize >= (1 + 2 * getPotatoSize())) {
                setPotatoSize(getPotatoSize() + 1);
                setFertilize(getFertilize() - 2 * getPotatoSize());
            }
        }
        super.onSyncedDataUpdated(id);
    }
    private void updatePotatoSizeInfo() {
        this.refreshDimensions();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(5 + 2 * getPotatoSize());
        this.setHealth(getMaxHealth());
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() == Items.BONE_MEAL) {
            setFertilize(getFertilize() + 1);
            for(int i = 0; i < 5; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), d0, d1, d2);
            }
            this.playSound(SoundEvents.BONE_MEAL_USE, 1.0F, 1.0F);

            if (!this.level().isClientSide) {
                itemstack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }
}