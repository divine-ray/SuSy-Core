package supersymmetry.common.metatileentities.multi.electric;

import gregicality.multiblocks.GregicalityMultiblocks;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing;
import gregicality.multiblocks.common.metatileentities.GCYMMetaTileEntities;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternStringError;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.BlockInfo;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.*;
import gregicality.multiblocks.common.block.blocks.BlockUniqueCasing.*;
import gregtech.common.blocks.BlockMachineCasing.MachineCasingType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.ArrayUtils;
import supersymmetry.api.recipes.SuSyRecipeMaps;
import supersymmetry.common.blocks.BlockSheetedFrame;
import supersymmetry.common.blocks.BlockTurbineRotor;
import supersymmetry.common.blocks.SheetedFrameItemBlock;
import supersymmetry.common.blocks.SuSyMetaBlocks;
import supersymmetry.common.metatileentities.SuSyMetaTileEntities;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Supplier;

import static supersymmetry.api.blocks.VariantHorizontalRotatableBlock.FACING;

public class MetaTileEntityStrandCaster extends RecipeMapMultiblockController {

    public MetaTileEntityStrandCaster(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, SuSyRecipeMaps.STRAND_CASTING);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityStrandCaster(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("F   ++PFPFPFPFs", "F   ++G G G G G", "FCCCFF         ", "FCCCF          ", " CCC           ")
                .aisle("      PSPSPSPSs", "      R R R R +", "CCCCPP         ", "CC#CC          ", "C###C          ")
                .aisle("      PSPSPSPSs", "  CCCCR R R R +", "CC##II         ", "C###C          ", "C###C          ")
                .aisle("      PSPSPSPSs", "      R R R R +", "CCCCPP         ", "CC#CC          ", "C###C          ")
                .aisle("F   FFPFPFPFPFs", "F   FFG G G G G", "FCXCFF         ", "FCCCF          ", " CCC           ")
                .where('X', selfPredicate())
                //.where('H', states(UniqueCasingType.HEAT_VENT.get()))
                .where('C', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.INVAR_HEATPROOF)))
                .where('P', states(MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE)))
                .where('G', states(MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STEEL_GEARBOX)))
                .where('s', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID)))
                .where('S', states(SuSyMetaBlocks.SHEETED_FRAMES.get(Materials.Steel).getBlock(Materials.Steel).withProperty(BlockSheetedFrame.SHEETED_FRAME_AXIS, BlockSheetedFrame.FrameEnumAxis.fromFacingAxis(RelativeDirection.FRONT.getRelativeFacing(getFrontFacing(), getUpwardsFacing(), isFlipped()).getAxis()))))
                .where('R', states(SuSyMetaBlocks.SHEETED_FRAMES.get(Materials.Invar).getBlock(Materials.Invar).withProperty(BlockSheetedFrame.SHEETED_FRAME_AXIS, BlockSheetedFrame.FrameEnumAxis.fromFacingAxis(RelativeDirection.FRONT.getRelativeFacing(getFrontFacing(), getUpwardsFacing(), isFlipped()).getAxis()))))
                .where('F', frames(Materials.Steel))
                .where('I', frames(Materials.Invar))
                .where('#', air())
                .where(' ', any())
                .where('+', frames(Materials.Steel)
                    .or(autoAbilities(true, true, true, true, true, true, false)))
                .build();
    }
/*
    protected TraceabilityPredicate sheetedFrameBoxOrientation() {
        //makes sure rotor's front faces the left side (relative to the player) of controller front
        EnumFacing leftAxis = RelativeDirection.RIGHT.getRelativeFacing(getFrontFacing(), getUpwardsFacing(), isFlipped());

        // converting the left facing to positive x or z axis direction
        // this is needed for the following update which converts this rotatable block from horizontal directional into axial directional.
        EnumFacing Axis = leftFacing.getIndex() < 4 ? EnumFacing.SOUTH : EnumFacing.WEST;

        Supplier<BlockInfo[]> supplier = () -> new BlockInfo[]{new BlockInfo(sheetedFrameBoxOrientation(.withRotation(FACING, Axis))};
        return new TraceabilityPredicate(blockWorldState -> {
            IBlockState state = blockWorldState.getBlockState();
            if (!(state.getBlock() instanceof BlockTurbineRotor)) return false;

            // auto-correct rotor orientation
            if (state != sheetedFrameBoxOrientation(withProperty(FACING, Axis)) {
                getWorld().setBlockState(blockWorldState.getPos(), steelRotorState().withProperty(FACING, Axis));
            }
            return true;
        }, supplier);
    }
    */
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.HEAT_PROOF_CASING;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.PYROLYSE_OVEN_OVERLAY;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }
    @Override
    public boolean hasMaintenanceMechanics(){
        return false;
    }

}
