package example
import processing.core.{PApplet, PVector}
import scala.collection.immutable.Range

class ProcessingTest extends PApplet {

  val WIDTH = 1024
  val HEIGHT = 768
  val DIAG_DIST = Math.sqrt(WIDTH * WIDTH + HEIGHT * HEIGHT)

  var circles: Seq[PVector] = Seq.empty

  val ROWS = 50
  val COLS = 50

  val colourRed = color(250, 112, 154)
  val colourOrange = color(254, 225, 64)

  case class Pointer(pos: PVector, dir: PVector)

  var pointers: Seq[Pointer] = {
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
    text(frameRate, 10, 20)
    val mousePos = new PVector(mouseX, mouseY)
    recalc(mousePos)
    // draw the pointers
    pointers.foreach(p => {
      val dist = mousePos.dist(p.pos)
      val amt =  dist / DIAG_DIST
      val colour = lerpColor(colourRed, colourOrange, amt.toFloat)
      val lineLength = 10f
      val end = p.pos.copy.add(p.dir.copy.mult(lineLength))
      strokeWeight(2)
      stroke(colour)
      line(p.pos.x, p.pos.y, end.x, end.y)
    })
  }

  def recalc(mousePos: PVector): Unit = {
    pointers = pointers.map(d => {
      val easing = d.pos.dist(mousePos) / DIAG_DIST * 0.5
      val targetDir = mousePos.copy.sub(d.pos).normalize
      val something = PVector.lerp(d.dir, targetDir, easing.toFloat).normalize
      d.copy(dir = something)
    })
  }
}
