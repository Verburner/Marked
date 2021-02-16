package theMarked.VFX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ImpactSparkEffect;

public class ElectrocuteEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float power;
    private float color_intensity;
    private TextureAtlas.AtlasRegion img = null;
    private float xscale;

    public ElectrocuteEffect(float x, float y, float power) {
        if (this.img == null) {
            this.img = ImageMaster.vfxAtlas.findRegion("combat/lightning");
            this.power = power;
        }

        this.x = x - (float)this.img.packedWidth/2.0F;
        this.y = y+50.0F*Settings.scale;
        if (power==0) color_intensity = 150.0F;
        color_intensity = 150.0F + (105.0F * 1-(1/power));
        this.color = CardHelper.getColor(255.0F,255.0F,color_intensity);
        this.duration = 0.5F + power/90.0F;
        this.startingDuration = 0.5F + power/90.0F;
        this.rotation = 90.0F;
        this.xscale = (1.0F/this.img.packedHeight)*(this.x-AbstractDungeon.player.drawX);
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            if (power>0) {
                if (power<6) {
                    CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.MED, false);
                }
                if (power<12) {
                    CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                }
                else CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.MED, false);
            }

            for(int i = 0; i < 3+power; ++i) {
                AbstractDungeon.topLevelEffectsQueue.add(new ImpactSparkEffect(this.x + MathUtils.random(-20.0F, 20.0F) * Settings.scale + 150.0F * Settings.scale, this.y + MathUtils.random(-20.0F, 20.0F) * Settings.scale));
            }
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        this.color.a = Interpolation.bounceIn.apply(this.duration * 2.0F);
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, 0.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale*xscale, this.scale, this.rotation);
        sb.setBlendFunction(770, 771);
        sb.setColor(Color.WHITE.cpy());
    }

    public void dispose() {
    }
}
