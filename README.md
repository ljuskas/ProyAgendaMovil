HerApp, surgió bajo la necesidad de Herald (mipadre), trabajador del rubro de ventas, utiliza sus correos para almacenar sus contactos.
La problemática surgió del momento en que ya ha superado los más de 3000 contactos, utiliza 2 correos y le es difícil gestionarlos.

La solución, gestionar sus cuentas de google en una aplicación dearrollada 100% en java, framework spring y capa web con thymeleaf.

Tecnologías:

-Java
-SpringBoot
-Lombok
-SpringData
-SpringSecurity
-Apis people (google)

Seguridad OAuth2

1. El sistema se debe logear con cuenta google, para esto debe integrar api de google para la autenticación.
2. El sistema debe importar la lista completa de contactos asociada a la cuenta de google y almacenarla en BD (MariaDb)
3. ContactoController debe permitir listarTodos, listarPorRut, listarPorMovil, listarPorNombre, listarPorEmpresa.
4. Usuario debe tener UUID, EMAIL, NOMBRE, TOKEN.
5. CONTACTO debe heredar de usuario y además contener: 
    Rut
    Empresa
    Teléfono móvil 
    Teléfono fijo
    Teléfono trabajo
    Fecha nacimiento 
    Notas
6. Finalmente permitirá exportar resultados de búsqueda de CONTACTO a formato csv o txt.
