import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar

class ProgressBarAnimation(progressBar: ProgressBar, from: Float, to: Float) :
    Animation() {
        private val progressBar: ProgressBar = progressBar
        private val from: Float
        private val to: Float

        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            super.applyTransformation(interpolatedTime, t)
            val value = from + (to - from) * interpolatedTime
            progressBar.progress = value.toInt()
        }

        init {
            this.from = from
            this.to = to
        }
}