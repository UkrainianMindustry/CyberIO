package net.liplum.blocks;

import arc.util.Nullable;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;
import net.liplum.CioMod;
import net.liplum.animations.anis.AniConfig;
import net.liplum.animations.anis.AniState;
import net.liplum.animations.anis.AniStateM;
import net.liplum.animations.anis.IAniSMed;

import java.util.Collection;
import java.util.HashMap;

public abstract class AniedBlock<TBlock extends Block, TBuild extends Building> extends Block implements IAniSMed<TBlock, TBuild> {
    protected final HashMap<String, AniState<TBlock, TBuild>> allAniStates = new HashMap<>();
    protected AniConfig<TBlock, TBuild> aniConfig;

    public AniedBlock(String name) {
        super(name);
        if (CioMod.AniStateCanLoad) {
            this.genAnimState();
            this.genAniConfig();
        }
    }

    @Override
    @Nullable
    public AniState<TBlock, TBuild> getAniStateByName(String name) {
        return allAniStates.get(name);
    }

    @Override
    public Collection<AniState<TBlock, TBuild>> getAllAniStates() {
        return allAniStates.values();
    }

    @Override
    public AniConfig<TBlock, TBuild> getAniConfig() {
        return aniConfig;
    }

    @Override
    public void setAniConfig(AniConfig<TBlock, TBuild> config) {
        aniConfig = config;
    }

    public AniState<TBlock, TBuild> addAniState(AniState<TBlock, TBuild> aniState) {
        allAniStates.put(aniState.getStateName(), aniState);
        return aniState;
    }

    public abstract class AniedBuild extends Building {
        protected AniStateM<TBlock, TBuild> aniStateM;

        @Override
        public Building create(Block block, Team team) {
            super.create(block, team);
            if (CioMod.AniStateCanLoad) {
                AniedBlock<TBlock, TBuild> outer = AniedBlock.this;
                this.aniStateM = outer.getAniConfig().gen((TBlock) outer, (TBuild) this);
            }
            return this;
        }

        public void fixedUpdateTile() {

        }

        public void fixedDraw() {

        }

        @Override
        public void updateTile() {
            super.updateTile();
            fixedUpdateTile();
            if (CioMod.AniStateCanLoad) {
                aniStateM.update();
            }
        }

        @Override
        public void draw() {
            super.draw();
            fixedDraw();
            if (CioMod.AniStateCanLoad) {
                aniStateM.drawBuilding();
            }
        }
    }
}