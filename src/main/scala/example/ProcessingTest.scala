package example
import processing.core.{PApplet, PVector}
import processing.event.KeyEvent

import scala.collection.immutable.Range
import scala.collection.parallel.ParSeq

class ProcessingTest extends PApplet {

  val WIDTH = 1024
  val HEIGHT = 768
  var circles: Seq[PVector] = Seq.empty

  val ROWS = 50
  val COLS = 50

  case class Pointer(pos: PVector, dir: PVector)

  var dingles: Seq[Pointer] = {
    val xSpacing = WIDTH.toFloat / (COLS - 1)
    val ySpacing = HEIGHT.toFloat / (ROWS - 1)
    for {
      x <- Range(0, COLS).map(_ * xSpacing)
      y <- Range(0, ROWS).map(_ * ySpacing)
    } yield Pointer(new PVector(x, y), PVector.random2D())
  }

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
    text(frameRate, 20, 20)
    val mousePos = new PVector(mouseX, mouseY)
    recalc(mousePos)
    dingles.foreach(p => {
      val lineLength = 10f
      val end = p.pos.copy.add(p.dir.copy.mult(lineLength))
      line(p.pos.x, p.pos.y, end.x, end.y)
    })

  }

  def recalc(mousePos: PVector): Unit = {
    val farthestDist = Math.sqrt(width * width + height * height)
    dingles = dingles.map(d => {
      val easing = d.pos.dist(mousePos) / farthestDist
      val targetDir = d.pos.copy.sub(mousePos).normalize
      val something = PVector.lerp(d.dir, targetDir, easing.toFloat).normalize
      d.copy(dir = something)
    })
  }
}
