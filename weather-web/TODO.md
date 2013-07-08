TODO
----
* Dividir el proyecto en modulos.
+ Revisar el codigo y eliminar avisos.
- Aplicar project-rules a checkstyle, PMD, etc.
- Revisar los avisos de plugins de Maven sin version.
- No incluir drivers JDBC en el entregable.
- Incluir perfiles a la aplicación.
- Mover de cdi-common la integracion con jaxws-rt fuera del proyecto.
- Crear una configuración para varios entornos (dev, prod).
- Mover las anotaciones especificas de Postgres (Secuencias), de las entities a orm.xml.
- Revisar el tema de los rangos en WeatherData, posiblemente eliminar endDate.
- Eliminar leak de InputStream en downloader-lib.
- Incluir un servicio para el envio de correo (http://docs.codehaus.org/display/JETTY/JNDI#JNDI-Configuringresource-refsandresource-env-refs).
- Añadir creacion de javadoc y sources en los proyectos que le corresponda.
- En weather-web heredar el proyecto padre y limpiar dependencias.
- En el parser de meteo galicia usar el nuevo modelo de objetos.
- Crear un configuracion para el site en el proyecto raiz.
- Meter las pruebas en los proyectos que le corresponda.
- Desplegar el site en GitHub.
+ Crear pagina usando JSF
    http://stackoverflow.com/questions/12334820/tomcat-cdi-arquillian
    


