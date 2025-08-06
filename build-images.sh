#!/bin/bash
echo "🧹 Eliminando imágenes anteriores..."
echo "🚀 Construyendo imágenes con prefijo tu-usuario/..."

# Compilar todos los proyectos
echo "📦 Compilando microservicios..."
mvn clean package -DskipTests

# Construir imágenes con prefijo correcto
echo "🐳 Construyendo imagen Central..."
cd microservicio-central
docker build -t tu-usuario/agroflow-central:latest .
cd ..

echo "🐳 Construyendo imagen Inventario..."
cd microservicio-inventario
docker build -t tu-usuario/agroflow-inventario:latest .
cd ..

echo "🐳 Construyendo imagen Facturación..."
cd microservicio-facturacion
docker build -t tu-usuario/agroflow-facturacion:latest .
cd ..

echo "✅ Imágenes construidas con prefijo tu-usuario/!"
docker images | grep tu-usuario/agroflow

echo ""
echo "📤 Para subir imágenes a Docker Hub:"
echo "docker push tu-usuario/agroflow-central:latest"
echo "docker push tu-usuario/agroflow-inventario:latest" 
echo "docker push tu-usuario/agroflow-facturacion:latest"