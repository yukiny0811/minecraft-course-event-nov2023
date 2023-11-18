package com.example.examplemod.mc_15_tobisuke;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelTobisuke<T extends EntityTobisuke> extends HierarchicalModel<T> {
    private final ModelPart mainRoot;

    public ModelTobisuke(ModelPart modelPart) {
        this.mainRoot = modelPart.getChild("mainRoot");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild(
                "mainRoot", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-3.0f, -1.0f, -7.0f, 6, 6, 6)
                        .texOffs(24, 0)
                        .addBox(-1.5f, 3.0f, -8.0f, 3, 1, 1)
                        .texOffs(0, 18)
                        .addBox(-4.5f, 4.0f, -7.5f, 9, 6, 12)
                        .texOffs(30, 0)
                        .addBox(-5.5f, 5.0f, -7.0f, 1, 4, 8)
                        .texOffs(30, 0)
                        .addBox(4.5f, 5.0f, -7.0f, 1, 4, 8),
                PartPose.offsetAndRotation(0f, 14f, 0f, 0f, 0f, 0f)
        );

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public ModelPart root() {
        return this.mainRoot;
    }

    @Override
    public void setupAnim(
            T entityIn,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        mainRoot.yRot = netHeadYaw / (180.0f / (float) Math.PI) * 0.25f;
    }
}