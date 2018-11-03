package example
import processing.core.{PApplet, PVector}
import processing.event.KeyEvent

class ProcessingTest extends PApplet {

  var circles: Seq[PVector] = Seq.empty

  override def settings(): Unit = {
    size(1024, 768)
    pixelDensity(2)
  }

  override def setup(): Unit = {
    background(0)
    stroke(255)
    noFill()
  }

  override def draw(): Unit = {
    background(0)
    if (mousePressed) circles = circles :+ new PVector(mouseX, mouseY)
    circles.foreach(p => ellipse(p.x, p.y, 20, 20))
  }

  override def keyPressed(event: KeyEvent): Unit = {
    if (event.getKey == 'c') {
      circles = Seq.empty
      background(0)
    }
  }
}
