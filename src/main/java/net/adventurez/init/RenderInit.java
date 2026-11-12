package net.adventurez.init;

import net.adventurez.AdventureMain;
import net.adventurez.block.renderer.*;
import net.adventurez.entity.model.*;
import net.adventurez.entity.render.*;
import net.adventurez.init.ParticleInit.ShardParticle;
import net.adventurez.init.ParticleInit.SprintParticle;
import net.adventurez.init.ParticleInit.VoidCloudParticle;
import net.adventurez.init.ParticleInit.FartParticle;
import net.neoforged.fml.ModList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public class RenderInit {

    private static final ResourceLocation WITHERED_TEXTURE = AdventureMain.identifierOf("textures/misc/withered.png");
    private static final ResourceLocation ACTIVE_ARMOR_TEXTURE = AdventureMain.identifierOf("textures/misc/active_armor.png");

    public static final boolean isCanvasLoaded = ModList.get().isLoaded("canvas");
    public static final boolean isIrisLoaded = ModList.get().isLoaded("iris");
    public static final boolean isSodiumLoaded = ModList.get().isLoaded("sodium");

    public static final ModelLayerLocation BLACKSTONE_GOLEM_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("blackstone_golem_render_layer"), "blackstone_golem_render_layer");
    public static final ModelLayerLocation THROWN_ROCK_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("thrown_rock_render_layer"), "thrown_rock_render_layer");
    public static final ModelLayerLocation GILDED_STONE_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("gilded_stone_render_layer"), "gilded_stone_render_layer");
    public static final ModelLayerLocation MINI_BLACKSTONE_GOLEM_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("mini_blackstone_golem_render_layer"), "mini_blackstone_golem_render_layer");
    public static final ModelLayerLocation PIGLIN_BEAST_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("piglin_beast_render_layer"), "piglin_beast_render_layer");
    public static final ModelLayerLocation NIGHTMARE_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("nightmare_render_layer"), "nightmare_render_layer");
    public static final ModelLayerLocation SOUL_REAPER_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("soul_reaper_render_layer"), "soul_reaper_render_layer");
    public static final ModelLayerLocation NECROMANCER_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("necromancer_render_layer"), "necromancer_render_layer");
    public static final ModelLayerLocation WITHER_PUPPET_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("wither_puppet_render_layer"), "wither_puppet_render_layer");
    public static final ModelLayerLocation SKELETON_VANGUARD_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("skeleton_vanguard_render_layer"), "skeleton_vanguard_render_layer");
    public static final ModelLayerLocation SUMMONER_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("summoner_render_layer"), "summoner_render_layer");
    public static final ModelLayerLocation BLAZE_GUARDIAN_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("blaze_guardian_render_layer"), "blaze_guardian_render_layer");
    public static final ModelLayerLocation THE_EYE_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("the_eye_render_layer"), "the_eye_render_layer");
    public static final ModelLayerLocation VOID_SHADOW_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("void_shadow_render_layer"), "void_shadow_render_layer");
    public static final ModelLayerLocation TINY_EYE_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("tiny_eye_render_layer"), "tiny_eye_render_layer");
    public static final ModelLayerLocation RED_FUNGUS_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("red_fungus_render_layer"), "red_fungus_render_layer");
    public static final ModelLayerLocation BROWN_FUNGUS_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("brown_fungus_render_layer"), "brown_fungus_render_layer");
    public static final ModelLayerLocation ORC_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("orc_render_layer"), "orc_render_layer");
    public static final ModelLayerLocation DRAGON_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("dragon_render_layer"), "dragon_render_layer");
    public static final ModelLayerLocation MAMMOTH_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("mammoth_render_layer"), "mammoth_render_layer");
    public static final ModelLayerLocation VOID_FRAGMENT_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("void_fragment_render_layer"), "void_fragment_render_layer");
    public static final ModelLayerLocation VOID_SHADE_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("void_shade_render_layer"), "void_shade_render_layer");
    public static final ModelLayerLocation VOID_BULLET_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("void_bullet_render_layer"), "void_bullet_render_layer");
    public static final ModelLayerLocation PIGLIN_FLAG_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("piglin_flag_render_layer"), "piglin_flag_render_layer");
    public static final ModelLayerLocation ENDER_WHALE_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("ender_whale_render_layer"), "ender_whale_render_layer");
    public static final ModelLayerLocation IGUANA_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("iguana_render_layer"), "iguana_render_layer");
    public static final ModelLayerLocation AMETHYST_GOLEM_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("amethyst_golem_render_layer"), "amethyst_golem_render_layer");
    public static final ModelLayerLocation AMETHYST_SHARD_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("amethyst_shard_render_layer"), "amethyst_shard_render_layer");
    public static final ModelLayerLocation DESERT_RHINO_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("desert_rhino_render_layer"), "desert_rhino_render_layer");
    public static final ModelLayerLocation SHAMAN_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("shaman_render_layer"), "shaman_render_layer");
    public static final ModelLayerLocation DEER_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("deer_render_layer"), "deer_render_layer");
    public static final ModelLayerLocation SKUNK_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("skunk_render_layer"), "skunk_render_layer");
    public static final ModelLayerLocation ENDERWARTHOG_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("enderwarthog_render_layer"), "enderwarthog_render_layer");
    public static final ModelLayerLocation DUBIOUS_PUMPKIN_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("dubious_pumpkin_render_layer"), "dubious_pumpkin_render_layer");
    public static final ModelLayerLocation CRYSTAL_BEETLE_LAYER = new ModelLayerLocation(AdventureMain.identifierOf("crystal_beetle_render_layer"), "crystal_beetle_render_layer");

    public static void init(IEventBus modBus) {
        modBus.addListener(EntityRenderersEvent.RegisterRenderers.class, RenderInit::registerRenderers);
        modBus.addListener(EntityRenderersEvent.RegisterLayerDefinitions.class, RenderInit::registerLayers);
        modBus.addListener(FMLClientSetupEvent.class, event -> event.enqueueWork(RenderInit::registerBlockRenderLayers));
        modBus.addListener(RegisterParticleProvidersEvent.class, RenderInit::registerParticles);
        modBus.addListener(RegisterGuiLayersEvent.class, event -> event.registerAboveAll(AdventureMain.identifierOf("hud"), RenderInit::renderHud));
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Entity Renderer
        event.registerEntityRenderer(EntityInit.BLACKSTONE_GOLEM, BlackstoneGolemRenderer::new);
        event.registerEntityRenderer(EntityInit.THROWN_ROCK, ThrownRockRenderer::new);
        event.registerEntityRenderer(EntityInit.GILDED_BLACKSTONE_SHARD, GildedStoneRenderer::new);
        event.registerEntityRenderer(EntityInit.MINI_BLACKSTONE_GOLEM, MiniBlackstoneGolemRenderer::new);
        event.registerEntityRenderer(EntityInit.PIGLIN_BEAST, PiglinBeastRenderer::new);
        event.registerEntityRenderer(EntityInit.NIGHTMARE, NightmareRenderer::new);
        event.registerEntityRenderer(EntityInit.SOUL_REAPER, SoulReaperRenderer::new);
        event.registerEntityRenderer(EntityInit.NECROMANCER, NecromancerRenderer::new);
        event.registerEntityRenderer(EntityInit.WITHER_PUPPET, WitherPuppetRenderer::new);
        event.registerEntityRenderer(EntityInit.SKELETON_VANGUARD, SkeletonVanguardRenderer::new);
        event.registerEntityRenderer(EntityInit.SUMMONER, SummonerRenderer::new);
        event.registerEntityRenderer(EntityInit.BLAZE_GUARDIAN, BlazeGuardianRenderer::new);
        event.registerEntityRenderer(EntityInit.THE_EYE, TheEyeRenderer::new);
        event.registerEntityRenderer(EntityInit.VOID_SHADOW, VoidShadowRenderer::new);
        event.registerEntityRenderer(EntityInit.TINY_EYE, TinyEyeRenderer::new);
        event.registerEntityRenderer(EntityInit.RED_FUNGUS, RedFungusRenderer::new);
        event.registerEntityRenderer(EntityInit.BROWN_FUNGUS, BrownFungusRenderer::new);
        event.registerEntityRenderer(EntityInit.ORC, OrcRenderer::new);
        event.registerEntityRenderer(EntityInit.DRAGON, DragonRenderer::new);
        event.registerEntityRenderer(EntityInit.MAMMOTH, MammothRenderer::new);
        event.registerEntityRenderer(EntityInit.VOID_FRAGMENT, VoidFragmentRenderer::new);
        event.registerEntityRenderer(EntityInit.VOID_SHADE, VoidShadeRenderer::new);
        event.registerEntityRenderer(EntityInit.VOID_BULLET, VoidBulletRenderer::new);
        event.registerEntityRenderer(EntityInit.FIRE_BREATH, NoopRenderer::new);
        event.registerEntityRenderer(EntityInit.BLAZE_GUARDIAN_SHIELD, NoopRenderer::new);
        event.registerEntityRenderer(EntityInit.ENDER_WHALE, EnderWhaleRenderer::new);
        event.registerEntityRenderer(EntityInit.IGUANA, IguanaRenderer::new);
        event.registerEntityRenderer(EntityInit.AMETHYST_GOLEM, AmethystGolemRenderer::new);
        event.registerEntityRenderer(EntityInit.AMETHYST_SHARD, AmethystShardRenderer::new);
        event.registerEntityRenderer(EntityInit.DESERT_RHINO, DesertRhinoRenderer::new);
        event.registerEntityRenderer(EntityInit.VOID_CLOUD, NoopRenderer::new);
        event.registerEntityRenderer(EntityInit.SHAMAN, ShamanRenderer::new);
        event.registerEntityRenderer(EntityInit.DEER, DeerRenderer::new);
        event.registerEntityRenderer(EntityInit.SKUNK, SkunkRenderer::new);
        event.registerEntityRenderer(EntityInit.ENDERWARTHOG, EnderwarthogRenderer::new);
        event.registerEntityRenderer(EntityInit.DUBIOUS_PUMPKIN, DubiousPumpkinRenderer::new);
        event.registerEntityRenderer(EntityInit.CRYSTAL_BEETLE, CrystalBeetleRenderer::new);

        // Block Entity Renderer
        event.registerBlockEntityRenderer(BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER_ENTITY, ChiseledPolishedBlackstoneHolderRenderer::new);
        event.registerBlockEntityRenderer(BlockInit.PIGLIN_FLAG_ENTITY, PiglinFlagRenderer::new);
        event.registerBlockEntityRenderer(BlockInit.SHADOW_CHEST_ENTITY, ChestRenderer::new);
    }

    private static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // Entity Layer
        event.registerLayerDefinition(BLACKSTONE_GOLEM_LAYER, BlackstoneGolemModel::getTexturedModelData);
        event.registerLayerDefinition(THROWN_ROCK_LAYER, RockModel::getTexturedModelData);
        event.registerLayerDefinition(GILDED_STONE_LAYER, GildedStoneModel::getTexturedModelData);
        event.registerLayerDefinition(MINI_BLACKSTONE_GOLEM_LAYER, MiniBlackstoneGolemModel::getTexturedModelData);
        event.registerLayerDefinition(PIGLIN_BEAST_LAYER, PiglinBeastModel::getTexturedModelData);
        event.registerLayerDefinition(SOUL_REAPER_LAYER, SoulReaperModel::getTexturedModelData);
        event.registerLayerDefinition(NECROMANCER_LAYER, NecromancerModel::getTexturedModelData);
        event.registerLayerDefinition(WITHER_PUPPET_LAYER, WitherPuppetModel::getTexturedModelData);
        event.registerLayerDefinition(SKELETON_VANGUARD_LAYER, SkeletonVanguardModel::getTexturedModelData);
        event.registerLayerDefinition(SUMMONER_LAYER, SummonerModel::getTexturedModelData);
        event.registerLayerDefinition(BLAZE_GUARDIAN_LAYER, BlazeGuardianModel::getTexturedModelData);
        event.registerLayerDefinition(THE_EYE_LAYER, TheEyeModel::getTexturedModelData);
        event.registerLayerDefinition(VOID_SHADOW_LAYER, VoidShadowModel::getTexturedModelData);
        event.registerLayerDefinition(TINY_EYE_LAYER, TinyEyeModel::getTexturedModelData);
        event.registerLayerDefinition(RED_FUNGUS_LAYER, RedFungusModel::getTexturedModelData);
        event.registerLayerDefinition(BROWN_FUNGUS_LAYER, BrownFungusModel::getTexturedModelData);
        event.registerLayerDefinition(ORC_LAYER, OrcModel::getTexturedModelData);
        event.registerLayerDefinition(DRAGON_LAYER, DragonModel::getTexturedModelData);
        event.registerLayerDefinition(MAMMOTH_LAYER, MammothModel::getTexturedModelData);
        event.registerLayerDefinition(VOID_FRAGMENT_LAYER, VoidFragmentModel::getTexturedModelData);
        event.registerLayerDefinition(VOID_SHADE_LAYER, VoidShadeModel::getTexturedModelData);
        event.registerLayerDefinition(VOID_BULLET_LAYER, VoidBulletModel::getTexturedModelData);
        event.registerLayerDefinition(PIGLIN_FLAG_LAYER, PiglinFlagRenderer::getTexturedModelData);
        event.registerLayerDefinition(ENDER_WHALE_LAYER, EnderWhaleModel::getTexturedModelData);
        event.registerLayerDefinition(IGUANA_LAYER, IguanaModel::getTexturedModelData);
        event.registerLayerDefinition(AMETHYST_GOLEM_LAYER, AmethystGolemModel::getTexturedModelData);
        event.registerLayerDefinition(AMETHYST_SHARD_LAYER, AmethystShardModel::getTexturedModelData);
        event.registerLayerDefinition(DESERT_RHINO_LAYER, DesertRhinoModel::getTexturedModelData);
        event.registerLayerDefinition(SHAMAN_LAYER, ShamanModel::getTexturedModelData);
        event.registerLayerDefinition(DEER_LAYER, DeerModel::getTexturedModelData);
        event.registerLayerDefinition(SKUNK_LAYER, SkunkModel::getTexturedModelData);
        event.registerLayerDefinition(ENDERWARTHOG_LAYER, EnderwarthogModel::getTexturedModelData);
        event.registerLayerDefinition(DUBIOUS_PUMPKIN_LAYER, DubiousPumpkinModel::getTexturedModelData);
        event.registerLayerDefinition(CRYSTAL_BEETLE_LAYER, CrystalBeetleModel::getTexturedModelData);
    }

    private static void registerBlockRenderLayers() {
        // Blocks (block entity renderers are registered in registerRenderers)
        ItemBlockRenderTypes.setRenderLayer(BlockInit.CHISELED_POLISHED_BLACKSTONE_HOLDER, RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockInit.PIGLIN_FLAG, RenderType.cutout());
    }

    private static void registerParticles(RegisterParticleProvidersEvent event) {
        // Particles
        event.registerSpriteSet(ParticleInit.AMETHYST_SHARD_PARTICLE, ShardParticle.ShardFactory::new);
        event.registerSpriteSet(ParticleInit.VOID_CLOUD_PARTICLE, VoidCloudParticle.CloudFactory::new);
        event.registerSpriteSet(ParticleInit.SPRINT_PARTICLE, SprintParticle.SprintFactory::new);
        event.registerSpriteSet(ParticleInit.FART_PARTICLE, FartParticle.Factory::new);
    }

    private static void renderHud(GuiGraphics drawContext, DeltaTracker tickDelta) {
        {
            Minecraft client = Minecraft.getInstance();
            if (!client.options.hideGui&& !client.player.isCreative()&& !client.player.isSpectator()) {
                ItemStack itemStack = client.player.getItemBySlot(EquipmentSlot.CHEST);
                if (!itemStack.isEmpty() && itemStack.is(ItemInit.GILDED_NETHERITE_CHESTPLATE)) {
                    if (itemStack.get(ItemInit.GILDED_DATA) != null && itemStack.get(ItemInit.GILDED_DATA).activated()) {
                        int scaledWidth = client.getWindow().getGuiScaledWidth();
                        int scaledHeight = client.getWindow().getGuiScaledHeight();
                        int worldTime = (int) client.player.level().getGameTime();
                        int savedTagInt = itemStack.get(ItemInit.GILDED_DATA).time();
                        float effectDuration = ConfigInit.CONFIG.gilded_netherite_armor_effect_duration;
                        drawContext.pose().pushPose();
                        if (savedTagInt + effectDuration > worldTime) {
                            int multiplier = 2;
                            if (savedTagInt + Math.max(effectDuration - 100, 0) < worldTime) {
                                multiplier = 4;
                            }

                            float pulsating = (float) Math.sin((float) ((worldTime * multiplier) - (savedTagInt - 1)) / 6.2831855F);
                            drawContext.setColor(1.0F, 1.0F, 1.0F, pulsating + 0.5F);
                            drawContext.blit(ACTIVE_ARMOR_TEXTURE, (scaledWidth / 2) - 5, scaledHeight - 49, 10, 10, 0, 0, 16, 16, 16, 16);
                        } else {
                            float fading = (1F - (((float) (worldTime - (savedTagInt + (effectDuration - 1f))) / effectDuration) - 0.4F));
                            drawContext.setColor(1.0F, 0.65F, 0.65F, fading);
                            drawContext.blit(ACTIVE_ARMOR_TEXTURE, (scaledWidth / 2) - 5, scaledHeight - 49, 10, 10, 0, 0, 16, 16, 16, 16);
                        }
                        drawContext.pose().popPose();
                        drawContext.setColor(1.0F, 1.0F, 1.0F, 1.0F);
                    }
                }
                if (client.player.hasEffect(EffectInit.WITHERING)) {
                    drawContext.setColor(1.0F, 1.0F, 1.0F, client.player.getEffect(EffectInit.WITHERING).getDuration() / 280F);
                    int scaledWidth = client.getWindow().getGuiScaledWidth();
                    int scaledHeight = client.getWindow().getGuiScaledHeight();
                    drawContext.blit(WITHERED_TEXTURE, scaledWidth / 2 - 64, scaledHeight / 2 - 64, 0.0F, 0.0F, 128, 128, 128, 128);
                    drawContext.setColor(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }
}