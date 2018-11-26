package sketch

import java.awt.font.NumericShaper

import processing.core.{PApplet, PVector}

import scala.collection.immutable.Range

class PlanetsSketch extends PApplet {

  val WIDTH = 1920
  val HEIGHT = 1080

  override def settings(): Unit = {
    size(WIDTH, HEIGHT)
    pixelDensity(2)
  }

  override def setup(): Unit = {
    background(0)
    stroke(255)
    noFill()
  }

  override def draw(): Unit = {
    background(0)
    text(frameRate, 10, 20)
    // draw the pointers
    drawCenterArcs(mouseX, mouseY)

    drawRays(millis / 30)
  }

  def drawCenterArcs(controlX: Int, controlY: Int): Unit = {
    strokeWeight(2)
    val wid = ((width / 2) - controlX) / 10
    val mousePos = new PVector(width / 2, controlY) // cp1
    val inverseMousePos = new PVector(width / 2, height - controlY)
    val leftPoint = new PVector(width / 2 - wid, height / 2)
    val rightPoint = new PVector(width / 2 + wid, height / 2)

    ellipse(mousePos.x, mousePos.y, 5, 5)
    ellipse(inverseMousePos.x, inverseMousePos.y, 5, 5)
    curve(mousePos.x, mousePos.y, leftPoint.x, leftPoint.y, rightPoint.x, rightPoint.y, mousePos.x, mousePos.y)
    curve(inverseMousePos.x, inverseMousePos.y, leftPoint.x, leftPoint.y, rightPoint.x, rightPoint.y, inverseMousePos.x, inverseMousePos.y)
  }

  def drawRays(time: Long): Unit = {
    pushMatrix()
    translate(width/2, height/2)
    rotate(time * 0.004f)
    Range(0,36).foreach{i =>
      pushMatrix()
      rotate(((Math.PI * 2 / 36) * i).toFloat)
      Range(1, 10).foreach{ j =>
        strokeWeight(10 - j)
        val lineWidth = Math.sin(time * 0.025).toFloat * 5
        val xMod = Math.sin(time * 0.005).toFloat * 400
        line(-lineWidth + xMod, -5 * j * 10, lineWidth + xMod, -5 * j * 10)
      }
      popMatrix()
    }
    popMatrix()
  }
}
