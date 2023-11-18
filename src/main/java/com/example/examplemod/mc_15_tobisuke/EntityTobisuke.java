package com.example.examplemod.mc_15_tobisuke;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class EntityTobisuke extends Mob {

    private static final EntityDataAccessor<Float> DATA_HEALTH_ID = SynchedEntityData.defineId(EntityTobisuke.class, EntityDataSerializers.FLOAT);

    public EntityTobisuke(EntityType<? extends Mob> entityTypeIn, Level level) {
        super(entityTypeIn, level);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3d)
                .add(Attributes.MAX_HEALTH, 8.0d)
                .add(Attributes.ATTACK_DAMAGE, 2.0d);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_HEALTH_ID, getHealth());
    }
}