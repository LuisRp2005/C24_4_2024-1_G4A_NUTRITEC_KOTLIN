import com.gutierrez.eddy.nutritec.models.Usuarios
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuariosApi {

    @GET("api/v1/verificar/{correo}")
    fun buscarUsuarioPorCorreo(@Path("correo") correo: String): Call<Usuarios>

    @POST("api/v1/usuario")
    fun guardarUsuario(@Body usuario: Usuarios): Call<Void>

}
