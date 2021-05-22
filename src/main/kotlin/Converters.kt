package hu.otilsoft.catalog

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.pow
import kotlin.math.roundToInt
import org.zkoss.bind.BindContext
import org.zkoss.bind.Converter
import org.zkoss.zk.ui.Component


class LocalDateTimeConverter : Converter<String, LocalDateTime, Component> {
    override fun coerceToUi(input: LocalDateTime?, component: Component?, bindContext: BindContext?): String =
        input?.format(DateTimeFormatter.ofPattern("y-MM-dd HH:mm:ss"))?: ""

    override fun coerceToBean(input: String?, component: Component?, bindContext: BindContext?): LocalDateTime {
        TODO("Not yet implemented")
    }
}


class DoubleConverter : Converter<String, Double, Component> {
    override fun coerceToUi(input: Double?, component: Component?, bindContext: BindContext?): String {
        val exp = 10.0.pow(1.0)
        val l = (input?.times(exp))?.roundToInt()
        return l?.div(exp)?.toString() ?: ""
    }

    override fun coerceToBean(input: String?, component: Component?, bindContext: BindContext?): Double? = input?.toDouble()
}
