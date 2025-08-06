#!/bin/bash

echo "🧹 Eliminando imágenes anteriores de AgroFlow..."

echo "🚀 Construyendo imágenes de AgroFlow con prefijo franciscoteran2001/..."

# Compilar todos los microservicios
echo "📦 Compilando microservicios de AgroFlow..."
mvn clean package -DskipTests

# Construir con prefijo correcto
echo "🐳 Construyendo imagen Central..."
cd microservicio-central
docker build -t franciscoteran2001/agroflow-central:latest .
cd ..

echo "🐳 Construyendo imagen Inventario..."
cd microservicio-inventario
docker build -t franciscoteran2001/agroflow-inventario:latest .
cd ..

echo "🐳 Construyendo imagen Facturación..."
cd microservicio-facturacion
docker build -t franciscoteran2001/agroflow-facturacion:latest .
cd ..

echo "✅ Imágenes de AgroFlow construidas con prefijo franciscoteran2001/!"
docker images | grep franciscoteran2001/agroflow

echo ""
echo "📤 Para subir imágenes a Docker Hub:"
echo "docker push franciscoteran2001/agroflow-central:latest"
echo "docker push franciscoteran2001/agroflow-inventario:latest"
echo "docker push franciscoteran2001/agroflow-facturacion:latest"

echo ""
echo "🚀 Para desplegar en Kubernetes ejecuta:"
echo "kubectl apply -f k8s/"