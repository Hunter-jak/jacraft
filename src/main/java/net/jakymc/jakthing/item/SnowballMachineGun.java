package net.jakymc.jakthing.item;

        import net.jakymc.jakthing.enchantment.JakEnchantment;
        import net.minecraft.network.chat.Component;
        import net.minecraft.sounds.SoundEvents;
        import net.minecraft.sounds.SoundSource;
        import net.minecraft.world.InteractionHand;
        import net.minecraft.world.InteractionResultHolder;
        import net.minecraft.world.entity.LivingEntity;
        import net.minecraft.world.entity.player.Player;
        import net.minecraft.world.entity.projectile.Snowball;
        import net.minecraft.world.item.*;
        import net.minecraft.world.item.enchantment.Enchantment;
        import net.minecraft.world.item.enchantment.EnchantmentHelper;
        import net.minecraft.world.item.enchantment.Enchantments;
        import net.minecraft.world.level.Level;
        import net.minecraft.world.phys.Vec3;

        import java.util.function.Predicate;


public class SnowballMachineGun extends ProjectileWeaponItem {
    public SnowballMachineGun() {
        super(new Item.Properties()
                .stacksTo(1)
        );
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack item) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack smg = player.getItemInHand(hand);
        if (!player.getAbilities().instabuild && player.getProjectile(smg).isEmpty()) {
            return InteractionResultHolder.fail(smg);
        }
        else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(smg);
        }
    }

    public void shootSnowball(LivingEntity entity, Level world,float multishot,float power) {
        Snowball snowball = new Snowball(world, entity);
        snowball.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 1.0F, 1.5F+(power/2), 1.5F+(multishot/1.5F));
        snowball.getPersistentData().putBoolean("smg",true);
        world.addFreshEntity(snowball);
    }

    @Override
    public void onUseTick(Level world, LivingEntity entity, ItemStack stack, int remainingUseTicks) {
        Player player = (Player) entity;
        ItemStack snowball = player.getProjectile(stack);
        int multisnowLevel = EnchantmentHelper.getItemEnchantmentLevel(JakEnchantment.MULTISNOW.get(), stack);
        int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
        if (!snowball.isEmpty() || player.getAbilities().instabuild){
            if(!world.isClientSide){
                if (remainingUseTicks > 0) {
                    for (int i = 0; i <= multisnowLevel; i++) {
                        shootSnowball(entity, world,multisnowLevel,powerLevel);
                    }
                    world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
                }
                if (!snowball.isEmpty() && !player.getAbilities().instabuild) {
                    snowball.shrink(1);
                    if (snowball.isEmpty()) {
                        player.getInventory().removeItem(snowball);
                    }
                }
            }
        }

    }
    public static final Predicate<ItemStack> SNOWBALLONLY = (stack) -> {
        return stack.is(Items.SNOWBALL);
    };

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return SNOWBALLONLY;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public int getEnchantmentValue() {
        return 10;
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack,enchantment)||enchantment==Enchantments.POWER_ARROWS;
    }
}