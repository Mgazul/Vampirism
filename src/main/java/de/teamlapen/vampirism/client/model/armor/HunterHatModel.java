package de.teamlapen.vampirism.client.model.armor;

import com.google.common.collect.ImmutableList;
import de.teamlapen.vampirism.client.core.ModEntitiesRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class HunterHatModel extends VampirismArmorModel {

    private static final String HAT_TOP = "hat_top";
    private static final String HAT_RIM = "hat_rim";

    public static LayerDefinition createLayer(float p_170683_, int type) {
        MeshDefinition mesh = VampirismArmorModel.createMesh();
        PartDefinition part = mesh.getRoot();
        if(type==1){
            part.addOrReplaceChild(HAT_TOP, CubeListBuilder.create().texOffs(0,31).mirror().addBox(-4,-14,-4,8,5,8), PartPose.offset(0,p_170683_,0));
            part.addOrReplaceChild(HAT_RIM, CubeListBuilder.create().texOffs(0,35).mirror().addBox(-6,-9,-6,8,3,8), PartPose.offset(0, p_170683_,0));
        }
        else{
            part.addOrReplaceChild(HAT_TOP, CubeListBuilder.create().texOffs(0,31).mirror().addBox(-4,-12,-4,-8,3,8), PartPose.offset(0,p_170683_,0));
            part.addOrReplaceChild(HAT_RIM, CubeListBuilder.create().texOffs(0,31).mirror().addBox(-8,-9,-8,16,1,16), PartPose.offset(0,p_170683_,0));
        }
        return LayerDefinition.create(mesh, 128 , 64);
    }
    private static  HunterHatModel hat0 ;
    private static HunterHatModel hat1 ;
    private final ModelPart hatTop;
    private final ModelPart hatRim;

    public static HunterHatModel getInstance0(){
        if(hat0 == null){
            hat0= new HunterHatModel( Minecraft.getInstance().getEntityModels().bakeLayer(ModEntitiesRender.HUNTER_HAT0));
        }
        return hat0;
    }

    public static HunterHatModel getInstance1(){
        if(hat1==null){
            hat1= new HunterHatModel( Minecraft.getInstance().getEntityModels().bakeLayer(ModEntitiesRender.HUNTER_HAT1));
        }
        return hat1;
    }


    public HunterHatModel(ModelPart part){
        super(part);
        this.hatTop = part.getChild(HAT_TOP);
        this.hatRim = part.getChild(HAT_RIM);
    }

    @Override
    public void setAllVisible(boolean invisible) {
        super.setAllVisible(false);
        hatRim.visible = true;
        hatTop.visible = true;
    }

    @Override
    protected Iterable<ModelPart> getHeadModels() {
        return ImmutableList.of(hatTop, hatRim);
    }
}
