package com.loopfor.pi

import java.awt.{AlphaComposite, Color, Font, RenderingHints}
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import scala.io.Source

object Pi {
  private val Rows = 500
  private val Cols = 2000
  private val Watermark = "π"

  def main(args: Array[String]): Unit = {
    val lines = Source.fromFile("pi-formatted.txt").getLines

    // Calculate width and height of single row of digits.
    val font = new Font("Source Code Pro", Font.PLAIN, 10)
    var gr = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics()
    val (width, height) = try {
      gr.setFont(font)
      val metrics = gr.getFontMetrics
      (metrics.stringWidth("0" * Cols), metrics.getHeight)
    } finally
      gr.dispose()

    val image = new BufferedImage(width, height * Rows, BufferedImage.TYPE_INT_ARGB)

    // Render all digits.
    gr = image.createGraphics()
    gr.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY)
    gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    gr.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY)
    gr.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE)
    gr.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)
    gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
    gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
    gr.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE)
    gr.setFont(font)
    gr.setColor(Color.BLACK)
    lines.foldLeft(0) { (r, line) =>
      gr.drawString(line, 0, (r + 1) * gr.getFontMetrics.getAscent)
      r + 1
    }

    // Render the Pi symbol overlay.
    gr.setFont(new Font("Symbol", Font.PLAIN, 4000))
    gr.setColor(Color.BLUE)
    gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.75F))
    val bounds = gr.getFontMetrics.getStringBounds(Watermark, gr)
    val x = (width - bounds.getWidth) / 2
    val y = (Rows * height) / 2
    gr.drawString(Watermark, x.toInt, y)
    gr.dispose()

    ImageIO.write(image, "PNG", new File("pi-poster.png"))
  }
}
