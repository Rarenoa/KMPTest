
import Foundation
import Vision

@objc class OCRExecutor: NSObject {
    @objc static let shared = VisionOcr()
        
    @objc func recognizeText(from imageData: Data, completion: @escaping (String?, Error?) -> Void) {
        guard let uiImage = UIImage(data: imageData),
              let cgImage = uiImage.cgImage else {
            completion(nil, NSError(domain: "VisionOcr", code: -1, userInfo: [NSLocalizedDescriptionKey: "Invalid image"]))
            return
        }
        
        let request = VNRecognizeTextRequest { request, error in
            if let error = error {
                completion(nil, error)
                return
            }
            guard let observations = request.results as? [VNRecognizedTextObservation] else {
                completion(nil, nil)
                return
            }
            let recognizedStrings = observations.compactMap { $0.topCandidates(1).first?.string }
            completion(recognizedStrings.joined(separator: "\n"), nil)
        }
        
        request.recognitionLanguages = ["ja"]
        request.recognitionLevel = .accurate
        request.usesLanguageCorrection = true
        
        let handler = VNImageRequestHandler(cgImage: cgImage, options: [:])
        DispatchQueue.global(qos: .userInitiated).async {
            do {
                try handler.perform([request])
            } catch {
                completion(nil, error)
            }
        }
}
