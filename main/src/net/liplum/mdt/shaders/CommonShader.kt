package net.liplum.mdt.shaders

import arc.Core
import arc.util.Time
import mindustry.Vars
import net.liplum.lib.shaders.FragFi
import net.liplum.lib.shaders.ShaderBase
import net.liplum.lib.shaders.VertFi

class CommonShader(
    vert: VertFi, frag: FragFi,
) : ShaderBase(vert, frag) {
    override fun apply() {
        setUniformf("u_time", Time.time)
        setUniformf("u_resolution",
            Core.graphics.width.toFloat(),
            Core.graphics.height
                .toFloat())
        setUniformf("u_offset",
            Core.camera.position.x,
            Core.camera.position.y
        )
        if (useEffectBuffer)
            Vars.renderer.effectBuffer.texture.bind(0)
    }
}