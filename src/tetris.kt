import sun.font.TrueTypeFont
import java.util.Scanner
//0
const val FIELD_WIDTH: Int = 12
const val FIELD_HEIGHT: Int = 18
const val MINO_WIDTH: Int = 4
const val MINO_HEIGHT: Int = 4

var minoType: Int = 0
var minoAngle: Int = 0
var minoX: Int = 5
var minoY: Int = 0
var fieldArray = Array(FIELD_HEIGHT, { Array(FIELD_WIDTH, { 0 }) })
var displayBuffer = Array(FIELD_HEIGHT, { Array(FIELD_WIDTH, { 0 }) })
var minoShapes =
    Array(MINO_TYPE.MAX.id, { Array(MINO_ANGLE.ANGLE_MAX.id, { Array(MINO_HEIGHT, { Array(MINO_WIDTH, { 0 }) }) }) })

enum class MINO_TYPE(val id: Int) {
    I(0),
    MAX(1)
}

enum class MINO_ANGLE(val id: Int) {
    ANGLE_0(0),
    ANGLE_MAX(1)
}

fun main(args: Array<String>) {

    minoShapes[0][0][0][0] = 0
    minoShapes[0][0][0][1] = 1
    minoShapes[0][0][0][2] = 0
    minoShapes[0][0][0][3] = 0
    minoShapes[0][0][1][0] = 0
    minoShapes[0][0][1][1] = 1
    minoShapes[0][0][1][2] = 0
    minoShapes[0][0][1][3] = 0
    minoShapes[0][0][2][0] = 0
    minoShapes[0][0][2][1] = 1
    minoShapes[0][0][2][2] = 0
    minoShapes[0][0][2][3] = 0
    minoShapes[0][0][3][0] = 0
    minoShapes[0][0][3][1] = 1
    minoShapes[0][0][3][2] = 0
    minoShapes[0][0][3][3] = 0

    for (i in 0 until FIELD_HEIGHT) {
        fieldArray[i][0] = 1
        fieldArray[i][FIELD_WIDTH - 1] = 1
    }
    for (j in 0 until FIELD_WIDTH) {
        fieldArray[FIELD_HEIGHT - 1][j] = 1
    }

    resetMino()

    while (true) {
        //println(minoX)
        //println(minoY)
        val key = readLine()
        when (key) {
            "s" -> if (isHit(minoX, minoY + 1, minoType, minoAngle)) {
                minoY++
            }
            "a" -> if (isHit(minoX - 1, minoY, minoType, minoAngle)) {
                minoX--
            }
            "d" -> if (isHit(minoX + 1, minoY, minoType, minoAngle)) {
                minoX++
            }
            else -> println("回転")
        }
        display(fieldArray)
        //println(!isHit(minoX, minoY + 1, minoType, minoAngle))
        if (!isHit(minoX, minoY + 1, minoType, minoAngle)) {
            for (i in 0 until MINO_HEIGHT) {
                for (j in 0 until MINO_WIDTH) {
                    if (minoX + j >= 0) {
                        if (minoY + i >= 0) {
                            if (minoX + j < FIELD_WIDTH) {
                                if (minoY + i < FIELD_HEIGHT) {
                                    fieldArray[minoY + i][minoX + j] =
                                        (fieldArray[minoY + i][minoX + j] or minoShapes[minoType][minoAngle][i][j])

                                }
                            }
                        }
                    }
                }
            }
            for (i in 0 until FIELD_HEIGHT - 1) {
                var lineFill: Boolean = true
                //println()
                for (j in 1 until FIELD_WIDTH - 1) {
                    //print(fieldArray[i][j])
                    if (fieldArray[i][j] == 0) {
                        lineFill = false
                    }
                }
                if (lineFill) {
                    //for (j in 1 until FIELD_WIDTH - 1) {
                    //fieldArray[i][j] = 0
                    //}
                    //for (j in i downTo 0 step 1){
                    for (i in FIELD_HEIGHT-1 downTo 1 step 1) {
                        //println()
                        //println(i)
                        for (j in 0 until FIELD_WIDTH) {
                            //print(j)
                            fieldArray[i][j] = fieldArray[i-1][j]
                        }
                    }
                    //}

                }
            }
            resetMino()
        } else {
            minoY++
        }
    }
}

fun resetMino() {
    minoX = 5
    minoY = 0
}

fun isHit(_minoX: Int, _minoY: Int, _minoType: Int, _minoAngle: Int): Boolean {
    for (i in 0 until MINO_HEIGHT) {
        for (j in 0 until MINO_WIDTH) {
            if (minoShapes[_minoType][_minoAngle][i][j] == 1) {
                if (fieldArray[_minoY + i][_minoX + j] == 1) {
                    return false
                }
            }
        }
    }
    return true
}

fun display(array: Array<Array<Int>>) {

    for (i in 0 until FIELD_HEIGHT) {
        for (j in 0 until FIELD_WIDTH) {
            displayBuffer[i][j] = fieldArray[i][j]
        }
    }

    for (i in 0 until MINO_HEIGHT) {
        for (j in 0 until MINO_WIDTH) {
            //println(minoY + i)
            //println(minoX + j)
            if (minoX + j >= 0) {
                if (minoY + i >= 0) {
                    if (minoX + j < FIELD_WIDTH) {
                        if (minoY + i < FIELD_HEIGHT) {
                            displayBuffer[minoY + i][minoX + j] =
                                (displayBuffer[minoY + i][minoX + j] or minoShapes[minoType][minoAngle][i][j])

                        }
                    }
                }
            }
        }
    }

    for (i in 0 until FIELD_HEIGHT) {
        for (j in 0 until FIELD_WIDTH) {
            if (displayBuffer[i][j] == 0) {
                print("・")
            } else {
                print("▫️")
            }
        }
        println()
    }
}
