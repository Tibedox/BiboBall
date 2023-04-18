package ru.tibedox.biboball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class BiBoBall extends ApplicationAdapter {
    public static final float WORLD_WIDTH = 16, WORLD_HEIGHT = 9;

	SpriteBatch batch;
	World world;
	OrthographicCamera camera;
	Vector3 touch;
	Box2DDebugRenderer debugRenderer;

	Texture imgBall, imgCube;

    StaticBox[] wall = new StaticBox[5];
	DynamicCircle ball;
    DynamicBox brick;
    DynamicPoly starball;
    KinematicCircle biba, boba;
    KinematicPoly star;

    @Override
    public void create() {
        batch = new SpriteBatch();
        imgBall = new Texture("arrow.png");
        imgCube = new Texture("box.png");
        // 0 = нет гравитации, -10 = гравитационная постоянная, 1 пункт = 1 метр
        // true - объекты спят или нет
        world = new World(new Vector2(0, -10), true);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        touch = new Vector3();

        debugRenderer = new Box2DDebugRenderer();

		wall[0] = new StaticBox(world, WORLD_WIDTH/2, WORLD_HEIGHT-0.1f, WORLD_WIDTH/2, 0.1f);
        wall[1] = new StaticBox(world, WORLD_WIDTH/2, 0.1f, WORLD_WIDTH/2, 0.1f);
		wall[2] = new StaticBox(world, WORLD_WIDTH-0.1f, WORLD_HEIGHT/2, 0.1f, WORLD_HEIGHT/2);
        wall[3] = new StaticBox(world, 0.1f, WORLD_HEIGHT/2, 0.1f, WORLD_HEIGHT/2);
        wall[4] = new StaticBox(world, WORLD_WIDTH/2, WORLD_HEIGHT/3, 0.1f, WORLD_HEIGHT/3);

		biba = new KinematicCircle(world, WORLD_WIDTH/4, 0.7f, 0.7f);
        boba = new KinematicCircle(world, WORLD_WIDTH/4*3, 0.7f, 0.7f);
        ball = new DynamicCircle(world, WORLD_WIDTH/2, WORLD_HEIGHT-1, 0.7f);
        ball.body.applyForceToCenter(100.0f*rnd(), 0f, true);
        starball = new DynamicPoly(world, WORLD_WIDTH/2-1.1f, WORLD_HEIGHT-1.1f);
        star = new KinematicPoly(world, WORLD_WIDTH/4, WORLD_HEIGHT/2, -4);
        new KinematicPoly(world, WORLD_WIDTH/4*3, WORLD_HEIGHT/2, 4);

        new KinematicPoly(world, WORLD_WIDTH/8, WORLD_HEIGHT/6, -5);
        new KinematicPoly(world, WORLD_WIDTH/8*7, WORLD_HEIGHT/6, 5);

        new KinematicPoly(world, WORLD_WIDTH/8*3, WORLD_HEIGHT/6, -5);
        new KinematicPoly(world, WORLD_WIDTH/8*5, WORLD_HEIGHT/6, 5);
        //brick = new Brick(world, WORLD_WIDTH/2, WORLD_HEIGHT-10, 8, 4);
        //brick.body.applyForceToCenter(10000.0f*rnd(), 0f, true);
    }

    int rnd(){
        return MathUtils.randomBoolean()?-1:1;
    }

    @Override
    public void render() {
        if (Gdx.input.isTouched(0)) {
            touch.set(Gdx.input.getX(0), Gdx.input.getY(0), 0);
            camera.unproject(touch);

            // если в левой половине поля
            if(touch.x<WORLD_WIDTH/2){
                touch(biba);
            }
            // если в правой половине поля
            if(touch.x>WORLD_WIDTH/2){
                touch(boba);
            }
        }
        if (Gdx.input.isTouched(1)) {
            touch.set(Gdx.input.getX(1), Gdx.input.getY(1), 0);
            camera.unproject(touch);

            // если в левой половине поля
            if(touch.x<WORLD_WIDTH/2){
                touch(biba);
            }
            // если в правой половине поля
            if(touch.x>WORLD_WIDTH/2){
                touch(boba);
            }

        }

        // обновление мира. 1/60f - шаг времени (от 1/60 до 1/240), другие 2 аргумента - velocityIterations и positionIterations
        world.step(1/60f, 6, 2);
        ball.update();
        //brick.update();

        move(biba);
        move(boba);

        ScreenUtils.clear(0.2f, 0, 0.3f, 1);
        debugRenderer.render(world, camera.combined);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBall, ball.x, ball.y, ball.width/2, ball.height/2, ball.width, ball.height,
                    1, 1, ball.angle, 0, 0, 500, 500, false, false);

        batch.end();
    }

    void touch(KinematicCircle b){
        Vector2 pos = b.body.getPosition();
        if(!b.isJump) {
            if (touch.y < 12 | Math.abs(touch.x-pos.x)>b.getR()*6) {
                b.body.setLinearVelocity((touch.x - pos.x) * 4, 0);
            } else {
                float g = (float) Math.sqrt((touch.x-pos.x)*(touch.x-pos.x) + (touch.y-pos.y)*(touch.y-pos.y));
                float sina = (touch.x-pos.x)/g;
                float cosa = (touch.y-pos.y)/g;
                b.jumpHigh = Math.abs(40*cosa);

                //b.body.setLinearVelocity((touch.x - pos.x) * 10, (touch.y - pos.y) * 10);
                b.body.setLinearVelocity(80*sina, 80*cosa);
                b.isJump = true;
            }
        }
    }

    void move(KinematicCircle b){
        if(b.isJump) {
            if(b.body.getLinearVelocity().y > 0) {
                if (b.getY() > b.jumpHigh) {
                    b.body.setLinearVelocity(b.body.getLinearVelocity().x/2, -40);
                }
            } else if(b.getY() < 8) {
                b.body.getLinearVelocity().y = 0;
                b.isJump = false;
            }
        } else {
            b.body.getLinearVelocity().y = 0;
        }

        // костыль
        if(b.isJump && b.body.getLinearVelocity().y == -40 && b.getY() < 8){
            b.body.getLinearVelocity().y = 0;
            b.isJump = false;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        imgBall.dispose();
        imgCube.dispose();
    }
}