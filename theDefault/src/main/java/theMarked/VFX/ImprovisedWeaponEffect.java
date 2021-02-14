package theMarked.VFX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ImprovisedWeaponEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float destY;
    private static final float DUR = 0.4F;
    private TextureAtlas.AtlasRegion img;
    private boolean playedSound = false;
    private boolean forcedAngle = false;
    private static Texture shoe;
    private TextureAtlas.AtlasRegion shoe_img;
    private float path;

    public ImprovisedWeaponEffect(float x, float y) {
        this.img = ImageMaster.DAGGER_STREAK;
        this.x = x;
        this.destY = y;
        this.y = this.destY + MathUtils.random(-25.0F, 25.0F) * Settings.scale - (float)this.img.packedHeight / 2.0F;
        this.startingDuration = 0.4F;
        this.duration = 0.4F;
        this.scale = Settings.scale;
        this.rotation = MathUtils.random(-3.0F, 3.0F);
        this.color = Color.WHITE.cpy();

        shoe = new Texture("theMarkedResources/images/char/defaultCharacter/vfx/shoe.png");
        shoe_img = new TextureAtlas.AtlasRegion(shoe,0,0,100,100);
    }

    public void update() {

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            CardCrawlGame.sound.play("ATTACK_FAST");
            this.isDone = true;
        }
        this.rotation += 5.0F;
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        path = duration/startingDuration;
        sb.draw(this.shoe_img, this.x-(1000.0F*path* Settings.scale), this.y, (float)this.shoe_img.packedWidth / 2.0F, (float)this.shoe_img.packedHeight / 2.0F, (float)this.shoe_img.packedWidth, (float)this.shoe_img.packedHeight, this.scale, this.scale, this.rotation);
        sb.setBlendFunction(770, 1);
    }

    public void dispose() {
    }
}
